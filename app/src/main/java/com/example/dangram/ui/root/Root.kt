package com.example.dangram.ui.root

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.dangram.components.root.RootComponent
import com.example.dangram.components.root.RootComponent.Child
import com.example.dangram.ui.app.App
import com.example.dangram.ui.auth.Auth
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamShapes

@Composable
fun Root(
    component: RootComponent
) {
    ChatTheme(
        shapes = StreamShapes.defaultShapes().copy(avatar = RoundedCornerShape(24.dp))
    ) {
        Children(
            stack = component.stack,
            animation = stackAnimation(fade() + scale())
        ) { child ->
            when (val instance = child.instance) {
                is Child.Auth -> {
                    Auth(component = instance.component)
                }
                is Child.App -> {
                    App(component = instance.component)
                }
            }
        }
    }
}