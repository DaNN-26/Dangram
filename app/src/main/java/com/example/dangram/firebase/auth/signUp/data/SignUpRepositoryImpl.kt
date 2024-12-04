package com.example.dangram.firebase.auth.signUp.data

import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.models.User
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignUpRepositoryImpl @Inject constructor(
    private val firebase: FirebaseAuth
) : SignUpRepository {
    override suspend fun signUp(email: String, password: String) =
        suspendCoroutine { continuation ->
            firebase.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if(user != null)
                        continuation.resume(User(id = user.uid, extraData = mapOf("name" to user.email.orEmpty())))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
    }
}