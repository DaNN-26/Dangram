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
            is SignInIntent.SignIn -> { signIn() }
            is SignInIntent.NavigateToSignUp -> { navigateToSignUp() }
            is SignInIntent.OnEmailChanged -> { state.update { it.copy(email = intent.email) } }
            is SignInIntent.OnPasswordChanged -> { state.update { it.copy(password = intent.password) } }
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
            }
        }
    }

    companion object {
        const val SIGN_IN_COMPONENT = "SIGN_IN_COMPONENT"
    }
}