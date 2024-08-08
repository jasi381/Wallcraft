package com.jasmeet.wallcraft.model.repoImpl

import android.content.ContentResolver
import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jasmeet.wallcraft.model.Collections
import com.jasmeet.wallcraft.model.repo.FirebaseRepo
import com.jasmeet.wallcraft.model.userInfo.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class FirebaseRepoImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val contentResolver: ContentResolver,

) : FirebaseRepo {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun saveUserInfo(currentUser: FirebaseUser) {

        currentUser.email?.let {
            UserInfo(
                name = currentUser.displayName ?: currentUser.email.toString().substringBefore("@"),
                email = it,
                uid = currentUser.uid,
                imgUrl = if (currentUser.photoUrl != null) currentUser.photoUrl.toString() else "https://images.unsplash.com/photo-1511367461989-f85a21fda167?q=80&w=1031&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

            )
        }?.let {
            db.collection(Collections.USER_COLLECTION).document(currentUser.uid).set(
                it
            ).await()
        }
    }
    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun fetchUserInfo(): UserInfo {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        val document = db.collection(Collections.USER_COLLECTION).document(uid).get().await()
        return document.toObject(UserInfo::class.java)
            ?: throw IllegalStateException("User not found")

    }


    override suspend fun updateUserImageAndName(imageUri: Uri?, newName: String?) {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        val updates = mutableMapOf<String, Any>()

        if (newName != null) {
            updates["name"] = newName
        }

        if (imageUri != null) {
            val imageUrl = uploadImageToFirebaseStorage(imageUri)
            updates["imgUrl"] = imageUrl
        }

        if (updates.isNotEmpty()) {
            db.collection(Collections.USER_COLLECTION).document(user.uid)
                .update(updates)
                .await()
        }
    }

    private suspend fun uploadImageToFirebaseStorage(imageUri: Uri): String {
        val user = auth.currentUser ?: throw IllegalStateException("User not logged in")
        val storageRef = storage.reference.child("profile_images/${user.uid}.jpg")

        return withContext(Dispatchers.IO) {
            val stream = contentResolver.openInputStream(imageUri)
                ?: throw IOException("Failed to open input stream for image URI")

            stream.use { inputStream ->
                storageRef.putStream(inputStream).await()
                storageRef.downloadUrl.await().toString()
            }
        }
    }




    override fun signOut() {
        auth.signOut()
    }
}