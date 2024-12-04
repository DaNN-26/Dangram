package com.example.dangram.ui.app.channels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.R
import com.example.dangram.components.app.channels.ChannelsComponent
import com.example.dangram.mvi.app.channels.ChannelsIntent
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen

@Composable
fun Channels(
    component: ChannelsComponent
) {
    val state by component.state.subscribeAsState()

    if(state.isConnected)
        ChannelsScreen(
            title = stringResource(id = R.string.app_name),
            onChannelClick = { component.processIntent(ChannelsIntent.NavigateToChannel(it)) },
            onHeaderActionClick = { component.processIntent(ChannelsIntent.NavigateToSearch) }
        )
    else
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Loading...")
        }
}