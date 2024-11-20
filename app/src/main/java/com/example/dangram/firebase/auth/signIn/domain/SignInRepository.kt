package com.example.dangram.firebase.auth.signIn.domain

import com.example.dangram.firebase.auth.model.User

interface SignInRepository {
    suspend fun signIn(email: String, password: String): User
}