package com.jatezzz.tvmaze.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jatezzz.tvmaze.R

class BiometricWrapper(private val fragment: Fragment) {

  private val executor = ContextCompat.getMainExecutor(fragment.requireContext())
  private val manager = BiometricManager.from(fragment.requireContext())

  fun isAvailable(): Boolean = (manager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS)

  fun showPrompt(onSuccess: () -> Unit, onError: () -> Unit) {
    val info = BiometricPrompt.PromptInfo.Builder()
      .setTitle(fragment.getString(R.string.app_name))
      .setDescription(fragment.getString(R.string.biometric_access))
      .setDeviceCredentialAllowed(false)
      .setNegativeButtonText(fragment.getString(R.string.cancel_title))
      .build()

    val prompt =
      BiometricPrompt(fragment, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
          super.onAuthenticationSucceeded(result)
          onSuccess()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
          super.onAuthenticationError(errorCode, errString)
          onError()
        }

        override fun onAuthenticationFailed() {
          super.onAuthenticationFailed()
          onError()
        }
      })
    prompt.authenticate(info)
  }
}
