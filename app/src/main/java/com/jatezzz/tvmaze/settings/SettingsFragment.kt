package com.jatezzz.tvmaze.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jatezzz.tvmaze.R
import com.jatezzz.tvmaze.base.BaseFragment
import com.jatezzz.tvmaze.dashboard.DashboardFragmentDirections
import com.jatezzz.tvmaze.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    R.layout.fragment_settings
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)
        binding.lifecycleOwner = this
        binding.buttonSetKey.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToAuthenticationFragment(true)
            try {
                findNavController().navigate(action)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
