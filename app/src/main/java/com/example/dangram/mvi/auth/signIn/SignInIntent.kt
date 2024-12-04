package com.example.dangram.mvi.auth.signIn

sealed class SignInIntent {
    class OnEmailChanged(val email: String) : SignInIntent()
    class OnPasswordChanged(val password: String) : SignInIntent()
    data object SignIn : SignInIntent()
    data object NavigateToSignUp : SignInIntent()
}