package com.example.dangram.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.dangram.components.root.RootComponent
import com.example.dangram.components.root.RootComponent.Child
import com.example.dangram.ui.app.App
import com.example.dangram.ui.auth.Auth

@Composable
fun Root(
    component: RootComponent
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(
            stack = component.stack,
            animation = stackAnimation(fade() + scale())
        ) { child ->
            when(val instance = child.instance) {
                is Child.Auth -> { Auth(component = instance.component) }
                is Child.App -> { App(component = instance.component) }
            }
        }
    }
}