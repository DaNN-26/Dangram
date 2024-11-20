package com.example.dangram.firebase.auth.signUp.domain

import com.example.dangram.firebase.auth.model.User

interface SignUpRepository {
    suspend fun signUp(email: String, password: String): User
}