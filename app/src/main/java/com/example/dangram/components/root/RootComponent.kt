package com.example.dangram.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.app.AppComponent
import com.example.dangram.components.auth.AuthComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Auth(val component: AuthComponent): Child
        class App(val component: AppComponent): Child
    }
}