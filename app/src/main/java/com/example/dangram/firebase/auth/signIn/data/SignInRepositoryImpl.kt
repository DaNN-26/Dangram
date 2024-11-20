package com.example.dangram.firebase.auth.signIn.data

import com.example.dangram.firebase.auth.model.User
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignInRepositoryImpl @Inject constructor(
    private val firebase: FirebaseAuth
) : SignInRepository {
    override suspend fun signIn(email: String, password: String) =
        suspendCoroutine { continuation ->
            firebase.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null)
                        continuation.resume(User(user.uid, user.email.orEmpty()))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}