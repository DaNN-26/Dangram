package com.example.dangram.components.app.messages

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.mvi.app.messages.MessagesIntent
import com.example.dangram.mvi.app.messages.MessagesState
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

class RealMessagesComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val channelId: String,
    private val navigateBack: () -> Unit
) : MessagesComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(MESSAGES_COMPONENT, MessagesState.serializer()) ?: MessagesState()
    )

    override val state = _state

    init {
        processIntent(MessagesIntent.LoadChannel)
    }

    override fun processIntent(intent: MessagesIntent) {
        when (intent) {
            is MessagesIntent.LoadChannel -> {
                watchChannel()
                _state.update { it.copy(channelId = channelId) }
                Log.d("MessagesComponent", "Updated channelId to ${state.value.channelId}")
            }
            is MessagesIntent.NavigateBack -> {
                unwatchChannel()
                navigateBack()
            }
        }
    }

    private fun watchChannel() {
            val channelClient = chatClient.channel(channelId)
            channelClient.watch().enqueue {
                Log.d("MessagesComponent", "Channel updated to ${it.getOrNull()}")
        }
    }

    private fun unwatchChannel() {
            val channelClient = chatClient.channel(channelId)
            channelClient.stopWatching().enqueue {
                Log.d("MessagesComponent", "Channel unwatched")
        }
    }

    companion object {
        const val MESSAGES_COMPONENT = "MESSAGES_COMPONENT"
    }
}