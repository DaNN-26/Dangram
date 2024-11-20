package com.example.dangram.components.app.channels

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.dangram.mvi.app.channels.ChannelsIntent
import com.example.dangram.mvi.app.channels.ChannelsState
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.InitializationState
import javax.inject.Inject

class RealChannelsComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val navigateToChannel: (Channel) -> Unit
) : ChannelsComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(CHANNELS_COMPONENT, ChannelsState.serializer()) ?: ChannelsState()
    )

    override val state = _state

    override fun processIntent(intent: ChannelsIntent) {
        when (intent) {
            is ChannelsIntent.NavigateToChannel -> { navigateToChannel(intent.channel) }
        }
    }

    override fun checkLoading() {
        val initState = chatClient.clientState.initializationState.value
        when(initState) {
            InitializationState.COMPLETE -> { _state.update { it.copy(isInitialized = true)} }
            InitializationState.INITIALIZING -> { _state.update { it.copy(isInitialized = false)} }
            InitializationState.NOT_INITIALIZED -> { _state.update { it.copy(isInitialized = false)} }
        }
    }

    companion object {
        const val CHANNELS_COMPONENT = "CHANNELS_COMPONENT"
    }
}