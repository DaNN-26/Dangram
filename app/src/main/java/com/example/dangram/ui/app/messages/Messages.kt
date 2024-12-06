package com.example.dangram.ui.app.messages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.app.messages.MessagesComponent
import com.example.dangram.mvi.app.messages.MessagesIntent
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.models.ReactionSortingByCount

@Composable
fun Messages(
    component: MessagesComponent
) {
    val state by component.state.subscribeAsState()
    val factory = MessagesViewModelFactory(
        context = LocalContext.current,
        channelId = state.channelId
    )
    MessagesScreen(
        viewModelFactory = factory,
        onBackPressed = { component.processIntent(MessagesIntent.NavigateBack) },
        reactionSorting = ReactionSortingByCount
    )
}