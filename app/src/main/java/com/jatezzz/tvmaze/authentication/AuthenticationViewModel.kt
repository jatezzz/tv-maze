package com.jatezzz.tvmaze.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jatezzz.tvmaze.common.persistance.KeyType
import com.jatezzz.tvmaze.common.persistance.SecureStorage
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isExitRequested = MutableLiveData<String>()
    val isExitRequested: LiveData<String> = _isExitRequested

    var isInSettingProcess: Boolean = false

    fun saveArgs(isInSettingProcess: Boolean) {
        this.isInSettingProcess = isInSettingProcess
    }

    fun submitPin(pinNumber: String) {
        if (isInSettingProcess) {
            SecureStorage.putString(KeyType.PASSWORD, pinNumber)
            _isExitRequested.value = REGISTRATION_DONE
            return
        }
        val savedPassword = SecureStorage.getString(KeyType.PASSWORD)
        if (savedPassword == pinNumber) {
            _isExitRequested.value = LOGIN_DONE
            return
        }
        _isExitRequested.value = ERROR
    }

}