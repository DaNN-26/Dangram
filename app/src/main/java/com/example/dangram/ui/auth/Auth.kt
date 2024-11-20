package com.example.dangram.ui.auth

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.dangram.components.auth.AuthComponent
import com.example.dangram.components.auth.AuthComponent.Child
import com.example.dangram.ui.auth.signUp.SignUp

@Composable
fun Auth(
    component: AuthComponent
) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when(val instance = child.instance) {
            is Child.SignUp -> { SignUp(component = instance.component) }
            is Child.SignIn -> {  }
        }
    }
}