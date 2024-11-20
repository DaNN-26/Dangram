package com.example.dangram.components.auth.signIn

import com.arkivanov.decompose.value.Value
import com.example.dangram.mvi.auth.signIn.SignInIntent
import com.example.dangram.mvi.auth.signIn.SignInState

interface SignInComponent {

    val state: Value<SignInState>

    fun processIntent(intent: SignInIntent)
}