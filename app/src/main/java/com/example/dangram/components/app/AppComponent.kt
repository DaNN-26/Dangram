package com.example.dangram.components.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.app.channels.ChannelsComponent
import com.example.dangram.components.app.messages.MessagesComponent
import com.example.dangram.components.app.search.SearchComponent

interface AppComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Channels(val component: ChannelsComponent): Child
        class Messages(val component: MessagesComponent): Child
        class Search(val component: SearchComponent): Child
    }
}