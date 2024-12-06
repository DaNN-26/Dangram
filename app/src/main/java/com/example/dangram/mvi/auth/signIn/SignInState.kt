package com.example.dangram.mvi.auth.signIn

import kotlinx.serialization.Serializable

@Serializable
data class SignInState(
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isPasswordVisible: Boolean = false
)
