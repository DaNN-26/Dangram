package com.example.dangram.mvi.auth.signUp

import kotlinx.serialization.Serializable

@Serializable
data class SignUpState(
    val email: String = "",
    val password: String = ""
)
