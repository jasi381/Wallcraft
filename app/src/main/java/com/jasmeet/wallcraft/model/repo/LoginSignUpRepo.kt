package com.jasmeet.wallcraft.model.repo

import com.google.firebase.auth.AuthResult

interface LoginSignUpRepo {
    suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun saveUserInfo(authResult: AuthResult)
    suspend fun sendPasswordResetEmail(email: String)
}