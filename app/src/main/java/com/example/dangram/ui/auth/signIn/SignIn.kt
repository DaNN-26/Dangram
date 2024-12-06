package com.example.dangram.ui.auth.signIn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.auth.signIn.SignInComponent
import com.example.dangram.mvi.auth.signIn.SignInIntent
import com.example.dangram.ui.auth.components.AuthScreen
import com.example.dangram.ui.auth.components.ErrorSnackbar

@Composable
fun SignIn(
    component: SignInComponent
) {
    val state by component.state.subscribeAsState()

    if(state.isError)
        ErrorSnackbar {
            component.processIntent(SignInIntent.UpdateIsError)
        }
    AuthScreen(
        email = state.email,
        password = state.password,
        onEmailChanged = { component.processIntent(SignInIntent.OnEmailChanged(it)) },
        onPasswordChanged = { component.processIntent(SignInIntent.OnPasswordChanged(it)) },
        isError = state.isError,
        isPasswordVisible = state.isPasswordVisible,
        updatePasswordVisibility = { component.processIntent(SignInIntent.UpdatePasswordVisibility(!state.isPasswordVisible)) },
        onButtonClick = { component.processIntent(SignInIntent.SignIn) },
        buttonText = "Sign In",
        navigateToAnotherScreen = { component.processIntent(SignInIntent.NavigateToSignUp) },
        textButtonText = "Create an account"
    )
}