package com.example.dangram.mvi.app.channels

import kotlinx.serialization.Serializable

@Serializable
data class ChannelsState(
    val isConnected: Boolean = false,
)
