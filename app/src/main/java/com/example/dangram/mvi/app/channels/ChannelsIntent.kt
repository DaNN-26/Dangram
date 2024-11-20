package com.example.dangram.mvi.app.channels

import io.getstream.chat.android.models.Channel

sealed class ChannelsIntent {
    class NavigateToChannel(val channel: Channel) : ChannelsIntent()
}