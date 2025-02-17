package com.example.dangram.components.app.messages

import com.arkivanov.decompose.value.Value
import com.example.dangram.mvi.app.messages.MessagesIntent
import com.example.dangram.mvi.app.messages.MessagesState

interface MessagesComponent {
    val state: Value<MessagesState>
    fun processIntent(intent: MessagesIntent)
}