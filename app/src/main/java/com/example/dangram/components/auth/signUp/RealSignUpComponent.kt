package com.example.dangram.components.auth.signUp

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import com.example.dangram.mvi.auth.signUp.SignUpIntent
import com.example.dangram.mvi.auth.signUp.SignUpState
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealSignUpComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val signUpRepository: SignUpRepository,
    private val navigateToApp: () -> Unit,
    private val chatClient: ChatClient,
    private val firebaseAuth: FirebaseAuth
) : SignUpComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_UP_COMPONENT, SignUpState.serializer()) ?: SignUpState()
    )

    override val state = _state

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.SignUp -> { signUp() }
            is SignUpIntent.OnEmailChanged -> { state.update { it.copy(email = intent.email) } }
            is SignUpIntent.OnPasswordChanged -> { state.update { it.copy(password = intent.password) } }
        }
    }

    private fun signUp() {
        coroutineScope.launch {
            try {
                signUpRepository.signUp(state.value.email, state.value.password)
                navigateToApp()
            } catch (e: Exception) {
                Log.d("SignUp Error", e.message.toString())
            }
        }
    }

    companion object {
        const val SIGN_UP_COMPONENT = "SIGN_UP_COMPONENT"
    }
}