package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.jasmeet.wallcraft.model.repo.LoginSignUpRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val repository: LoginSignUpRepo,
) : ViewModel() {


    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState: StateFlow<AuthResult?> = _authState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val debounceTimeMillis = 5000L
    private var lastErrorShownTime = 0L

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val state = MutableStateFlow(false)
    val stateFlow: StateFlow<Boolean> = state

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

    fun signUpEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.signUpWithEmailAndPassword(email, password)
                _authState.value = result
                repository.saveUserInfo(result)
                _isLoading.value = false
                state.value = true
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
                state.value = false
            }
        }
    }

    fun loginWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.loginWithEmailAndPassword(email, password)
                _authState.value = result
                _isLoading.value = false
                state.value = true
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
                state.value = false
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
                state.value = true

            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
                state.value = false
            }

        }
    }
}