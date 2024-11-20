package com.example.dangram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.dangram.components.root.RealRootComponent
import com.example.dangram.firebase.auth.di.AuthModule
import com.example.dangram.stream.di.StreamModule
import com.example.dangram.ui.root.Root
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val firebaseAuth = AuthModule.provideFirebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val offlinePluginFactory = 
            StreamOfflinePluginFactory(appContext = applicationContext)
        val statePluginFactory =
            StreamStatePluginFactory(config = StatePluginConfig(), appContext = this)

        val rootComponent = RealRootComponent(
            componentContext = defaultComponentContext(),
            chatClient = StreamModule.provideChatClient(
                appContext = this,
                offlinePluginFactory = offlinePluginFactory,
                statePluginFactory = statePluginFactory
            ),
            firebaseAuth = firebaseAuth,
            signInRepository = AuthModule.provideSignInRepository(firebaseAuth),
            signUpRepository = AuthModule.provideSignUpRepository(firebaseAuth)
        )

        setContent {
            Root(component = rootComponent)
        }
    }
}
