package com.example.dangram.firebase.auth.signIn.domain

import io.getstream.chat.android.models.User

interface SignInRepository {
    suspend fun signIn(email: String, password: String): User

}