package com.example.dangram.ui.auth.signUp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.auth.signUp.SignUpComponent
import com.example.dangram.mvi.auth.signUp.SignUpIntent
import com.example.dangram.ui.auth.components.AuthScreen
import com.example.dangram.ui.auth.components.ErrorSnackbar

@Composable
fun SignUp(
    component: SignUpComponent
) {
    val state by component.state.subscribeAsState()

    if(state.isError)
        ErrorSnackbar {
            component.processIntent(SignUpIntent.UpdateIsError)
        }
    AuthScreen(
        email = state.email,
        password = state.password,
        onEmailChanged = { component.processIntent(SignUpIntent.OnEmailChanged(it)) },
        onPasswordChanged = { component.processIntent(SignUpIntent.OnPasswordChanged(it)) },
        isError = state.isError,
        isPasswordVisible = state.isPasswordVisible,
        updatePasswordVisibility = { component.processIntent(SignUpIntent.UpdatePasswordVisibility(!state.isPasswordVisible)) },
        onButtonClick = { component.processIntent(SignUpIntent.SignUp) },
        buttonText = "Sign Up",
        navigateToAnotherScreen = { component.processIntent(SignUpIntent.NavigateToSignIn) },
        textButtonText = "Do you have an account?"
    )
}