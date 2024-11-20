package com.example.dangram.components.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.auth.signIn.SignInComponent
import com.example.dangram.components.auth.signUp.SignUpComponent

interface AuthComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SignUp(val component: SignUpComponent): Child
        class SignIn(val component: SignInComponent): Child
    }
}