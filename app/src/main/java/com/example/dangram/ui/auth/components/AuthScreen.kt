package com.example.dangram.ui.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun AuthScreen(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    isError: Boolean,
    isPasswordVisible: Boolean,
    updatePasswordVisibility: () -> Unit,
    onButtonClick: () -> Unit,
    buttonText: String,
    navigateToAnotherScreen: () -> Unit,
    textButtonText: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = email,
            label = { Text(text = "Email") },
            onValueChange = { onEmailChanged(it) },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ChatTheme.colors.inputBackground,
                unfocusedContainerColor = ChatTheme.colors.inputBackground,
                cursorColor = ChatTheme.colors.primaryAccent,
                selectionColors = TextSelectionColors(ChatTheme.colors.primaryAccent, ChatTheme.colors.primaryAccent),
                focusedIndicatorColor = ChatTheme.colors.primaryAccent,
                focusedLabelColor = ChatTheme.colors.primaryAccent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            label = { Text(text = "Password") },
            onValueChange = { onPasswordChanged(it) },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if(!isPasswordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = updatePasswordVisibility) {
                    Icon(
                        imageVector = if(!isPasswordVisible)
                            Icons.Rounded.Visibility
                        else
                            Icons.Rounded.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ChatTheme.colors.inputBackground,
                unfocusedContainerColor = ChatTheme.colors.inputBackground,
                cursorColor = ChatTheme.colors.primaryAccent,
                selectionColors = TextSelectionColors(ChatTheme.colors.primaryAccent, ChatTheme.colors.primaryAccent),
                focusedIndicatorColor = ChatTheme.colors.primaryAccent,
                focusedLabelColor = ChatTheme.colors.primaryAccent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(ChatTheme.colors.primaryAccent)
        ) {
            Text(text = buttonText)
        }
        TextButton(onClick = navigateToAnotherScreen) {
            Text(
                text = textButtonText,
                color = ChatTheme.colors.primaryAccent,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}