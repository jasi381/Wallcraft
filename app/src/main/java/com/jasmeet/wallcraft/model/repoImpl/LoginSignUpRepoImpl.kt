package com.jasmeet.wallcraft.model.repoImpl

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.wallcraft.model.Collections
import com.jasmeet.wallcraft.model.repo.LoginSignUpRepo
import com.jasmeet.wallcraft.model.userInfo.UserInfo
import kotlinx.coroutines.tasks.await

class LoginSignUpRepoImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : LoginSignUpRepo {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun saveUserInfo(authResult: AuthResult) {
        val user = authResult.user ?: return
        db.collection(Collections.USER_COLLECTION).document(user.uid).set(
            UserInfo(
                name = user.displayName ?: user.email.toString().substringBefore("@"),
                email = user.email,
                uid = user.uid,
                imgUrl = if (user.photoUrl != null) user.photoUrl.toString() else "https://images.unsplash.com/photo-1511367461989-f85a21fda167?q=80&w=1031&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

            )
        ).await()
    }
}