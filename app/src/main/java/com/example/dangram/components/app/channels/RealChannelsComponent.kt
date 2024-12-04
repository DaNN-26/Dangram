package com.example.dangram.components.app.channels

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.dangram.mvi.app.channels.ChannelsIntent
import com.example.dangram.mvi.app.channels.ChannelsState
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RealChannelsComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val navigateToChannel: (Channel) -> Unit,
    private val navigateToSearch: () -> Unit
) : ChannelsComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(CHANNELS_COMPONENT, ChannelsState.serializer()) ?: ChannelsState()
    )

    override val state = _state

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        observeConnectionState()
    }

    override fun processIntent(intent: ChannelsIntent) {
        when (intent) {
            is ChannelsIntent.NavigateToChannel -> { navigateToChannel(intent.channel) }
            is ChannelsIntent.NavigateToSearch -> { navigateToSearch() }
        }
    }

    override fun onCleared() {
        scope.coroutineContext.cancelChildren()
        onCleared()
    }

    private fun observeConnectionState() {
        chatClient.clientState.connectionState
            .onEach { state ->
                when (state) {
                    ConnectionState.Connected -> {
                        _state.update { it.copy(isConnected = true) }
                    }
                    else -> {
                        _state.update { it.copy(isConnected = false) }
                    }
                }
            }
            .launchIn(scope)
    }

    companion object {
        const val CHANNELS_COMPONENT = "CHANNELS_COMPONENT"
    }
}