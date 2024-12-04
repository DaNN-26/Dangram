package com.example.dangram.ui.auth.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.auth.signUp.SignUpComponent
import com.example.dangram.mvi.auth.signUp.SignUpIntent
import io.getstream.chat.android.compose.ui.theme.ChatTheme

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
            label = { Text(text = "Email") },
            onValueChange = { component.processIntent(SignUpIntent.OnEmailChanged(email = it)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ChatTheme.colors.inputBackground,
                unfocusedContainerColor = ChatTheme.colors.inputBackground,
                errorContainerColor = ChatTheme.colors.primaryAccent,
                cursorColor = ChatTheme.colors.primaryAccent,
                selectionColors = TextSelectionColors(ChatTheme.colors.primaryAccent, ChatTheme.colors.primaryAccent),
                focusedIndicatorColor = ChatTheme.colors.primaryAccent,
                focusedLabelColor = ChatTheme.colors.primaryAccent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.password,
            label = { Text(text = "Password") },
            onValueChange = { component.processIntent(SignUpIntent.OnPasswordChanged(password = it)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ChatTheme.colors.inputBackground,
                unfocusedContainerColor = ChatTheme.colors.inputBackground,
                errorContainerColor = ChatTheme.colors.primaryAccent,
                cursorColor = ChatTheme.colors.primaryAccent,
                selectionColors = TextSelectionColors(ChatTheme.colors.primaryAccent, ChatTheme.colors.primaryAccent),
                focusedIndicatorColor = ChatTheme.colors.primaryAccent,
                focusedLabelColor = ChatTheme.colors.primaryAccent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { component.processIntent(SignUpIntent.SignUp) },
            colors = ButtonDefaults.buttonColors(ChatTheme.colors.primaryAccent)
        ) {
            Text(text = "Sign Up")
        }
        TextButton(onClick = { component.processIntent(SignUpIntent.NavigateToSignIn) }) {
            Text(
                text = "Do you have an account?",
                color = ChatTheme.colors.primaryAccent,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}