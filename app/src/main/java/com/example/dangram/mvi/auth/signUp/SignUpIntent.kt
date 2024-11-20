package com.example.dangram.mvi.auth.signUp

sealed class SignUpIntent {
    class OnEmailChanged(val email: String) : SignUpIntent()
    class OnPasswordChanged(val password: String) : SignUpIntent()
    data object SignUp : SignUpIntent()
}