package com.example.dangram.components.app.channels

import com.arkivanov.decompose.value.Value
import com.example.dangram.mvi.app.channels.ChannelsIntent
import com.example.dangram.mvi.app.channels.ChannelsState

interface ChannelsComponent {
    val state: Value<ChannelsState>

    fun processIntent(intent: ChannelsIntent)

    fun onCleared()
}