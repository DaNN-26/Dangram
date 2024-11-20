package com.example.dangram.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.app.RealAppComponent
import com.example.dangram.components.auth.RealAuthComponent
import com.example.dangram.components.root.RootComponent.Child
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import io.getstream.chat.android.client.ChatClient
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RealRootComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val firebaseAuth: FirebaseAuth,
    private val signInRepository: SignInRepository,
    private val signUpRepository: SignUpRepository
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Auth,
            serializer = Config.serializer(),
            handleBackButton = false,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext) =
        when(config) {
            Config.Auth -> Child.Auth(component = authComponent(componentContext))
            Config.App -> Child.App(component = appComponent(componentContext))
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun authComponent(componentContext: ComponentContext) =
        RealAuthComponent(
            componentContext = componentContext,
            signInRepository = signInRepository,
            signUpRepository = signUpRepository,
            navigateToApp = { navigation.push(Config.App) },
            firebaseAuth = firebaseAuth
        )

    private fun appComponent(componentContext: ComponentContext) =
        RealAppComponent(
            componentContext = componentContext,
            chatClient = chatClient,
            firebaseAuth = firebaseAuth
        )

    @Serializable
    sealed interface Config {
        @Serializable
        data object Auth : Config
        @Serializable
        data object App : Config
    }
}