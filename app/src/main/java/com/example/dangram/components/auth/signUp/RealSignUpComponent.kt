package com.example.dangram.components.auth.signUp

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import com.example.dangram.mvi.auth.signUp.SignUpIntent
import com.example.dangram.mvi.auth.signUp.SignUpState
import io.getstream.chat.android.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealSignUpComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val signUpRepository: SignUpRepository,
    private val navigateToSignIn: () -> Unit,
    private val navigateToApp: () -> Unit,
    private val connectUser: (User) -> Unit
) : SignUpComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_UP_COMPONENT, SignUpState.serializer()) ?: SignUpState()
    )

    override val state = _state

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.SignUp -> { signUp() }
            is SignUpIntent.NavigateToSignIn -> { navigateToSignIn() }
            is SignUpIntent.OnEmailChanged -> { state.update { it.copy(email = intent.email) } }
            is SignUpIntent.OnPasswordChanged -> { state.update { it.copy(password = intent.password) } }
        }
    }

    private fun signUp() {
        scope.launch {
            try {
                signUpRepository.signUp(
                    email = state.value.email,
                    password = state.value.password
                ).let { user ->
                    connectUser(user)
                }
                navigateToApp()
            } catch (e: Exception) {
                Log.d("FirebaseSignUp Error", e.message.toString())
            }
        }
    }

    companion object {
        const val SIGN_UP_COMPONENT = "SIGN_UP_COMPONENT"
    }
}