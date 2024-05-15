package com.jasmeet.wallcraft.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.jasmeet.wallcraft.model.repo.FirebaseRepo
import com.jasmeet.wallcraft.model.userInfo.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val repository: FirebaseRepo,
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState: StateFlow<AuthResult?> = _authState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val debounceTimeMillis = 5000L
    private var lastErrorShownTime = 0L

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            while (true) {
                delay(debounceTimeMillis)
                setErrorMessage(null)
            }
        }
    }

    fun setErrorMessage(errorMessage: String?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (errorMessage != null && (currentTimeMillis - lastErrorShownTime) >= debounceTimeMillis) {
            _errorState.value = errorMessage
            lastErrorShownTime = currentTimeMillis

        } else if (errorMessage == null) {
            _errorState.value = null
            lastErrorShownTime = 0L
        }
    }

    fun signUpEmailPassword(email: String, password: String, onSignUp: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.signUpWithEmailAndPassword(email, password)
                _authState.value = result
                repository.saveUserInfo(result)
                _isLoading.value = false
                onSignUp()
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }
        }
    }

    fun loginWithEmailPassword(email: String, password: String, onLogin: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.loginWithEmailAndPassword(email, password)
                _authState.value = result
                _isLoading.value = false
                onLogin()
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }
        }
    }

    fun resetPassword(email: String, onResetPassword: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.sendPasswordResetEmail(email)
                _isLoading.value = false
                onResetPassword()
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }
        }
    }

    //This function will only be used when user tries to sign in with google
    fun saveData(authResult: AuthResult) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.saveUserInfo(authResult)
                _isLoading.value = false

            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }

        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val userInfo = repository.fetchUserInfo()
                _userInfo.value = userInfo
                Log.d("UserInfo", userInfo.email.toString())
            } catch (e: Exception) {
                setErrorMessage(e.message)
            }
        }
    }
}