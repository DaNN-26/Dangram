package com.example.dangram.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.dangram.components.auth.AuthComponent.Child
import com.example.dangram.components.auth.signIn.RealSignInComponent
import com.example.dangram.components.auth.signUp.RealSignUpComponent
import com.example.dangram.firebase.auth.signIn.domain.SignInRepository
import com.example.dangram.firebase.auth.signUp.domain.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RealAuthComponent @Inject constructor(
    private val componentContext: ComponentContext,
    private val signInRepository: SignInRepository,
    private val signUpRepository: SignUpRepository,
    private val navigateToApp: () -> Unit,
    private val firebaseAuth: FirebaseAuth
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

    private fun signUpComponent(componentContext: ComponentContext) =
        RealSignUpComponent(
            componentContext = componentContext,
            signUpRepository = signUpRepository,
            navigateToApp = navigateToApp,
            firebaseAuth = firebaseAuth
        )

    private fun signInComponent(componentContext: ComponentContext) =
        RealSignInComponent(
            componentContext = componentContext,
            signInRepository = signInRepository,
            navigateToApp = navigateToApp
        )

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignUp : Config
        @Serializable
        data object SignIn : Config
    }
}