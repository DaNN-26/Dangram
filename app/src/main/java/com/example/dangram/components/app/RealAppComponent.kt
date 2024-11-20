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
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RealAppComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val firebaseAuth: FirebaseAuth
) : AppComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    init {
        connectUser()
    }

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
            is Config.Channels -> Child.Channels(component = channelsComponent(componentContext))
            is Config.Messages -> Child.Messages(
                component = messagesComponent(
                    config = Config.Messages(config.channelId),
                    componentContext = componentContext
                )
            )
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun channelsComponent(componentContext: ComponentContext) =
        RealChannelsComponent(
            componentContext = componentContext,
            chatClient = chatClient,
            navigateToChannel = { channel ->
                navigation.push(Config.Messages(channel.cid))
            }
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

    private fun connectUser() {
        val firebaseUser = firebaseAuth.currentUser

        val user =
            User(
                id = firebaseUser?.uid.toString(),
                name = firebaseUser?.email.toString()
            )

        val token = chatClient.devToken(userId = user.id)

        chatClient.connectUser(
            user = user,
            token = token
        ).enqueue()
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Channels : Config
        @Serializable
        data class Messages(val channelId: String) : Config
    }
}