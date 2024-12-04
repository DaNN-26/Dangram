package com.example.dangram.firebase.auth.signUp.domain

import io.getstream.chat.android.models.User

interface SignUpRepository {
    suspend fun signUp(email: String, password: String): User
}