package com.example.dangram.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.auth.AuthComponent.Child
import com.example.dangram.components.auth.signIn.RealSignInComponent
import com.example.dangram.components.auth.signUp.RealSignUpComponent
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RealAuthComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val chatClient: ChatClient,
    private val signInRepository: SignInRepository,
    private val signUpRepository: SignUpRepository,
    private val navigateToApp: () -> Unit
) : AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.SignUp,
            serializer = Config.serializer(),
            handleBackButton = false,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext) =
        when(config) {
            is Config.SignUp -> Child.SignUp(component = signUpComponent(componentContext))
            is Config.SignIn -> Child.SignIn(component = signInComponent(componentContext))
        }

    @OptIn(DelicateDecomposeApi::class)
    private fun signUpComponent(componentContext: ComponentContext) =
        RealSignUpComponent(
            componentContext = componentContext,
            signUpRepository = signUpRepository,
            navigateToSignIn = { navigation.push(Config.SignIn) },
            navigateToApp = navigateToApp,
            connectUser = ::connectUser
        )

    private fun signInComponent(componentContext: ComponentContext) =
        RealSignInComponent(
            componentContext = componentContext,
            signInRepository = signInRepository,
            navigateToSignUp = { navigation.pop() },
            navigateToApp = navigateToApp,
            connectUser = ::connectUser
        )

    private fun connectUser(user: User) {
        val token = chatClient.devToken(userId = user.id)

        chatClient.connectUser(
            user = user,
            token = token
        ).enqueue()
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignUp : Config
        @Serializable
        data object SignIn : Config
    }
}