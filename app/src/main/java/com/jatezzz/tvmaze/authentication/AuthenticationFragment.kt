package com.jatezzz.tvmaze.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.biometric.BiometricAccessPopUp
import com.jatezzz.tvmaze.biometric.BiometricWrapper
import com.jatezzz.tvmaze.common.persistance.KeyType
import com.jatezzz.tvmaze.common.persistance.SecureStorage
import com.jatezzz.tvmaze.databinding.FragmentAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: AuthenticationViewModel

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    private lateinit var biometricWrapper: BiometricWrapper

    private var biometricAccessPopUp: BiometricAccessPopUp? = null

    private val loadingObserver = Observer<Boolean> {

    }
    private val exitObserver = Observer<String> {
        when (it) {
            "REGISTRATION_DONE" -> {
                startBiometricRegistration()
            }
            "LOGIN_DONE" -> {
                goToListFragment()
            }
            else -> {
                showError()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAuthenticationBinding.bind(view)
        binding.lifecycleOwner = this

        model = ViewModelProvider(this, viewModelFactory)[AuthenticationViewModel::class.java]

        model.isLoading.observe(viewLifecycleOwner, loadingObserver)
        model.isExitRequested.observe(viewLifecycleOwner, exitObserver)

        binding.viewmodel = model
        biometricWrapper = BiometricWrapper(this)
        binding.biometricButton.setOnClickListener {
            tryBiometricLogin()
        }
        binding.buttonEnter.setOnClickListener {
            model.submitPin(binding.textInputPin.editText?.text.toString())
        }

        val args: AuthenticationFragmentArgs by navArgs()
        model.saveArgs(args.isInSettingProcess)

        if (SecureStorage.getString(KeyType.PASSWORD).isNotEmpty() && !model.isInSettingProcess) {
            binding.biometricButton.visibility = View.VISIBLE
        }
    }

    private fun startBiometricRegistration() {
        val password = SecureStorage.getString(KeyType.PASSWORD)
        if (password.isEmpty()) {
            showError()
            goToListFragment()
            return
        }
        if (isBiometricAvailable() && !SecureStorage.getBoolean(KeyType.DO_NOT_ASK_AGAIN)) {
            askBiometricPermission(password)
        } else {
            goToListFragment()
        }
    }

    private fun tryBiometricLogin() {
        biometricWrapper.showPrompt({
            val password = SecureStorage.getString(KeyType.PASSWORD)
            if (password.isEmpty()) {
                showError()
                return@showPrompt
            }
            if (isBiometricAvailable()) {
                biometricWrapper.showPrompt({
                    goToListFragment()
                }, {
                    showError()
                })
            } else {
                showError()
            }
        }, {
            showError()
        })
    }

    private fun isBiometricAvailable(): Boolean = biometricWrapper.isAvailable()

    private fun askBiometricPermission(password: String) {
        view?.post {
            biometricAccessPopUp = BiometricAccessPopUp(requireActivity()).also {
                it.acceptAction = {
                    showBiometricAccess(password)
                    it.dismiss()
                }
                it.cancelAction = {
                    goToListFragment()
                    it.dismiss()
                }
                it.doNotAskAgainAction = {
                    SecureStorage.putBoolean(KeyType.DO_NOT_ASK_AGAIN, true)
                    goToListFragment()
                    it.dismiss()
                }
                it.showDialog()
            }
        }
    }

    private fun showBiometricAccess(password: String) {
        biometricWrapper.showPrompt({
            SecureStorage.putString(KeyType.PASSWORD, password)
            goToListFragment()
        }, {
            showError()
            goToListFragment()
        })
    }

    private fun showError() {
        Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT)
            .apply { show() }
    }

    private fun goToListFragment() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToDashboardFragment()
        try {
            findNavController().navigate(action)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
