package com.example.dangram.mvi.app.messages

import kotlinx.serialization.Serializable

@Serializable
data class MessagesState(
    val channelId: String = ""
)
