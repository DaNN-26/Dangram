package com.example.dangram.ui.auth.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.auth.signUp.SignUpComponent
import com.example.dangram.mvi.auth.signUp.SignUpIntent

@Composable
fun SignUp(
    component: SignUpComponent
) {
    val state by component.state.subscribeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.email,
            onValueChange = { component.processIntent(SignUpIntent.OnEmailChanged(email = it)) }
        )
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        TextField(
            value = state.password,
            onValueChange = { component.processIntent(SignUpIntent.OnPasswordChanged(password = it)) }
        )
        Button(onClick = { component.processIntent(SignUpIntent.SignUp) }) {
            Text(text = "Sign Up")
        }
    }
}