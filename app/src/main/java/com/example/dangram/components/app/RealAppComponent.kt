package com.example.dangram.components.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.app.AppComponent.Child
import com.example.dangram.components.app.channels.RealChannelsComponent
import com.example.dangram.components.app.messages.RealMessagesComponent
import com.example.dangram.components.app.search.RealSearchComponent
import com.example.dangram.stream.repository.domain.StreamRepository
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.client.ChatClient
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RealAppComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val firebaseAuth: FirebaseAuth,
    private val streamRepository: StreamRepository
) : AppComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Channels,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext) =
        when(config) {
            is Config.Channels -> Child.Channels(
                component = channelsComponent(componentContext)
            )
            is Config.Messages -> Child.Messages(
                component = messagesComponent(
                    config = Config.Messages(config.channelId),
                    componentContext = componentContext
                )
            )
            is Config.Search -> Child.Search(
                component = searchComponent(componentContext)
            )
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun channelsComponent(componentContext: ComponentContext) =
        RealChannelsComponent(
            componentContext = componentContext,
            chatClient = chatClient,
            navigateToChannel = { channel ->
                navigation.push(Config.Messages(channel.cid))
            },
            navigateToSearch = { navigation.push(Config.Search) }
        )

    private fun messagesComponent(
        config: Config.Messages,
        componentContext: ComponentContext
    ) =
        RealMessagesComponent(
            componentContext = componentContext,
            channelId = config.channelId,
            navigateBack = { navigation.pop() }
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun searchComponent(componentContext: ComponentContext) =
        RealSearchComponent(
            componentContext = componentContext,
            firebaseAuth = firebaseAuth,
            streamRepository = streamRepository,
            navigateBack = { navigation.pop() },
            navigateToChannel = { channel ->
                navigation.push(Config.Messages(channel.cid))
            }
        )

    @Serializable
    sealed interface Config {
        @Serializable
        data object Channels : Config
        @Serializable
        data class Messages(val channelId: String) : Config
        @Serializable
        data object Search : Config
    }
}