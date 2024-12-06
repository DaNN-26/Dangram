package com.example.dangram.components.auth.signIn

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.example.dangram.mvi.auth.signIn.SignInIntent
import com.example.dangram.mvi.auth.signIn.SignInState
import io.getstream.chat.android.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealSignInComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val signInRepository: SignInRepository,
    private val navigateToSignUp: () -> Unit,
    private val navigateToApp: () -> Unit,
    private val connectUser: (User) -> Unit
) : SignInComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_IN_COMPONENT, SignInState.serializer()) ?: SignInState()
    )

    override val state = _state

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.OnEmailChanged -> { _state.update { it.copy(email = intent.email) } }
            is SignInIntent.OnPasswordChanged -> { _state.update { it.copy(password = intent.password) } }
            is SignInIntent.UpdateIsError -> { _state.update { it.copy(isError = false) } }
            is SignInIntent.UpdatePasswordVisibility -> { _state.update { it.copy(isPasswordVisible = intent.isVisible) } }
            is SignInIntent.SignIn -> { signIn() }
            is SignInIntent.NavigateToSignUp -> { navigateToSignUp() }
        }
    }

    private fun signIn() {
        scope.launch {
            try {
                signInRepository.signIn(
                    email = state.value.email,
                    password = state.value.password
                ).let { user ->
                    connectUser(user)
                }
                navigateToApp()
            } catch (e: Exception) {
                Log.d("FirebaseSignIn Error", e.message.toString())
                _state.update { it.copy(isError = true) }
            }
        }
    }

    companion object {
        const val SIGN_IN_COMPONENT = "SIGN_IN_COMPONENT"
    }
}