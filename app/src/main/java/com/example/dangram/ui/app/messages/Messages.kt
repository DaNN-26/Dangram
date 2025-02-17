package com.example.dangram.ui.app.messages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    val context = LocalContext.current

    val factory = remember {
        mutableStateOf<MessagesViewModelFactory?>(null)
    }

    key(state.channelId) {
        factory.value = MessagesViewModelFactory(
            context = context,
            channelId = state.channelId
        )
    }

    MessagesScreen(
        viewModelFactory = factory.value!!,
        onBackPressed = { component.processIntent(MessagesIntent.NavigateBack) },
        reactionSorting = ReactionSortingByCount
    )
}