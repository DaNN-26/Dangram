package com.example.dangram.mvi.auth.signIn

sealed class SignInIntent {
    class OnEmailChanged(val email: String) : SignInIntent()
    class OnPasswordChanged(val password: String) : SignInIntent()
    data object UpdateIsError : SignInIntent()
    class UpdatePasswordVisibility(val isVisible: Boolean) : SignInIntent()
    data object SignIn : SignInIntent()
    data object NavigateToSignUp : SignInIntent()
}