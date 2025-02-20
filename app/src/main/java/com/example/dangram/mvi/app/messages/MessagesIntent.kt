package com.example.dangram.mvi.app.messages

sealed class MessagesIntent {
    data object LoadChannel : MessagesIntent()
    data object NavigateBack : MessagesIntent()
}