package com.example.dangram.mvi.app.channels

import io.getstream.chat.android.models.InitializationState
import kotlinx.serialization.Serializable

@Serializable
data class ChannelsState(
    val isInitialized: Boolean = false,
)
