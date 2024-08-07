package com.jasmeet.wallcraft.model.repo

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.jasmeet.wallcraft.model.userInfo.UserInfo

interface FirebaseRepo {
    suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun saveUserInfo(currentUser: FirebaseUser)
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun fetchUserInfo(): UserInfo
    suspend fun updateUserImageAndName(imageUri: Uri?, newName: String?)
    fun signOut()
}