package com.example.dangram.components.auth.signUp

import com.arkivanov.decompose.value.Value
import com.example.dangram.mvi.auth.signUp.SignUpIntent
import com.example.dangram.mvi.auth.signUp.SignUpState

interface SignUpComponent {

    val state: Value<SignUpState>

    fun processIntent(intent: SignUpIntent)
}