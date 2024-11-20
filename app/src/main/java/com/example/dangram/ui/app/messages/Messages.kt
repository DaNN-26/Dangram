package com.example.dangram.ui.app.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.app.messages.MessagesComponent
import com.example.dangram.mvi.app.messages.MessagesIntent
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.models.ReactionSortingByCount

@Composable
fun Messages(
    component: MessagesComponent
) {
    val state by component.state.subscribeAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        MessagesScreen(
            viewModelFactory = MessagesViewModelFactory(
                context = LocalContext.current,
                channelId = state.channelId,
                messageLimit = 30
            ),
            onBackPressed = { component.processIntent(MessagesIntent.NavigateBack) },
            reactionSorting = ReactionSortingByCount
        )
    }
}