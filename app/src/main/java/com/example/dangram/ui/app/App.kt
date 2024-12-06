package com.example.dangram.ui.app

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.dangram.components.app.AppComponent
import com.example.dangram.components.app.AppComponent.Child
import com.example.dangram.ui.app.channels.Channels
import com.example.dangram.ui.app.messages.Messages
import com.example.dangram.ui.app.search.Search

@Composable
fun App(
    component: AppComponent
) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (val instance = child.instance) {
            is Child.Channels -> {
                Channels(component = instance.component)
            }
            is Child.Messages -> {
                Messages(component = instance.component)
            }
            is Child.Search -> {
                Search(component = instance.component)
            }
        }
    }
}