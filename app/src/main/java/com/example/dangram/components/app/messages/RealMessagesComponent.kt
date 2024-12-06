package com.example.dangram.components.app.messages

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.mvi.app.messages.MessagesIntent
import com.example.dangram.mvi.app.messages.MessagesState
import javax.inject.Inject

class RealMessagesComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val channelId: String,
    private val navigateBack: () -> Unit
) : MessagesComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(MESSAGES_COMPONENT, MessagesState.serializer()) ?: MessagesState()
    )

    override val state = _state

    init {
        processIntent(MessagesIntent.LoadChannel)
        Log.d("ChannelID", channelId)
    }

    override fun processIntent(intent: MessagesIntent) {
        when (intent) {
            is MessagesIntent.LoadChannel -> _state.update { it.copy(channelId = channelId) }
            is MessagesIntent.NavigateBack -> navigateBack()
        }
    }

    companion object {
        const val MESSAGES_COMPONENT = "MESSAGES_COMPONENT"
    }
}