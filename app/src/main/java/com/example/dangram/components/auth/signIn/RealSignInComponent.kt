package com.example.dangram.components.auth.signIn

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.example.dangram.mvi.auth.signIn.SignInIntent
import com.example.dangram.mvi.auth.signIn.SignInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealSignInComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val signInRepository: SignInRepository,
    private val navigateToApp: () -> Unit
) : SignInComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_IN_COMPONENT, SignInState.serializer()) ?: SignInState()
    )

    override val state = _state

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun processIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.SignIn -> { signIn() }
            is SignInIntent.OnEmailChanged -> { state.update { it.copy(email = intent.email) } }
            is SignInIntent.OnPasswordChanged -> { state.update { it.copy(password = intent.password) } }
        }
    }

    private fun signIn() {
        coroutineScope.launch {
            try {
                signInRepository.signIn(state.value.email, state.value.password)
                navigateToApp()
            } catch (e: Exception) {
                Log.d("SignIn Error", e.message.toString())
            }
        }
    }

    companion object {
        const val SIGN_IN_COMPONENT = "SIGN_IN_COMPONENT"
    }
}