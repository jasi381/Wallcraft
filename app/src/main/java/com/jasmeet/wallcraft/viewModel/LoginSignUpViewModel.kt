package com.jasmeet.wallcraft.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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

    private val _updateSuccess = MutableStateFlow<Long?>(null)
    val updateSuccess: StateFlow<Long?> = _updateSuccess

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
                result.user?.let { repository.saveUserInfo(it) }
                _isLoading.value = false
                onSignUp()
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }
        }
    }

    fun signInWithGoogle(
        googleIdOptions: GetGoogleIdOption,
        context: Context,
        onSuccess: () -> Unit,
    ) {
        _isLoading.value = true
        val credentialManager = CredentialManager.create(context)
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()



        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )

                val credential = result.credential

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(googleIdToken, null)

                val auth = getInstance()

                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _isLoading.value = false
                            auth.currentUser?.let { saveData(it) }
                            onSuccess()
                        } else {
                            setErrorMessage(task.exception?.message)
                            _isLoading.value = false

                        }
                    }


            } catch (e: Exception) {
                setErrorMessage(e.message)
            }
        }
    }

    fun loginWithEmailPassword(email: String, password: String, onLogin: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.loginWithEmailAndPassword(email, password)
                _isLoading.value = false
                onLogin()
            } catch (e: Exception) {
                setErrorMessage(e.message)
                _isLoading.value = false
            }
        }
    }

    fun updateUserInfo(imageUri: Uri?, newName: String?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateUserImageAndName(imageUri, newName)
                _isLoading.value = false
                _updateSuccess.value = System.currentTimeMillis()
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
    fun saveData(currentUser: FirebaseUser) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.saveUserInfo(currentUser)
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
            } catch (e: Exception) {
                setErrorMessage(e.message)
            }
        }
    }

    fun signOut(onSignOut: () -> Unit, credentialManager: CredentialManager) {
        _isLoading.value = true
        try {
            repository.signOut()
            viewModelScope.launch {
                credentialManager.clearCredentialState(
                    ClearCredentialStateRequest()
                )
            }
            onSignOut.invoke()

        } catch (e: Exception) {
            setErrorMessage(e.message)
            Log.d("TAG", "signOut: ${e.message}")
        } finally {
            _isLoading.value = false
        }

    }
}