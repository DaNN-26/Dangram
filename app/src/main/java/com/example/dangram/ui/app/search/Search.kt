package com.example.dangram.ui.app.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dangram.components.app.search.SearchComponent
import com.example.dangram.mvi.app.search.SearchIntent
import com.example.dangram.stream.model.FoundUser
import io.getstream.chat.android.compose.ui.components.avatar.UserAvatar
import io.getstream.chat.android.compose.ui.components.poll.PollDialogHeader
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.User

@Composable
fun Search(
    component: SearchComponent
) {
    val state by component.state.subscribeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            PollDialogHeader(
                title = "User Search",
                onBackPressed = {
                    if(state.isUserFounded)
                        component.processIntent(SearchIntent.SetFalseIsUserFounded)
                    else
                        component.processIntent(SearchIntent.NavigateBack)
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.email,
                onValueChange = { component.processIntent(SearchIntent.OnEmailChanged(it)) },
                label = { Text(text = "Email") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ChatTheme.colors.inputBackground,
                    unfocusedContainerColor = ChatTheme.colors.inputBackground,
                    cursorColor = ChatTheme.colors.primaryAccent,
                    selectionColors = TextSelectionColors(ChatTheme.colors.primaryAccent, ChatTheme.colors.primaryAccent),
                    focusedIndicatorColor = ChatTheme.colors.primaryAccent,
                    focusedLabelColor = ChatTheme.colors.primaryAccent
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !state.isUserFounded,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                isError = state.isUserNull || state.isCurrentUser,
                supportingText = {
                    Text(
                        text = if(state.isUserNull)
                            "User not found"
                        else if(state.isCurrentUser)
                            "This is your email address"
                        else
                            "Enter the email of the user you want to find"
                    )
                },
                keyboardActions = KeyboardActions(onDone = {
                    component.processIntent(SearchIntent.SearchByEmail)
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    component.processIntent(SearchIntent.SearchByEmail)
                    keyboardController?.hide()
                },
                colors = ButtonDefaults.buttonColors(ChatTheme.colors.primaryAccent),
                enabled = !state.isUserFounded,
                modifier = Modifier.fillMaxWidth()
            ) {
                    Text(text = "Search")
                }
            }
            AnimatedVisibility(
                visible = state.isUserFounded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(255, 255, 255, 190))
                ) {
                    UserCard(
                        user = state.foundUser,
                        onSendMessageClick = { component.processIntent(SearchIntent.CreateChannel) }
                    )
                }
            }
    }
}

@Composable
fun UserCard(
    user: FoundUser?,
    onSendMessageClick: () -> Unit
) {
        val streamUser by remember {
            mutableStateOf(
                User(
                    id = user?.id.toString(),
                    name = user?.email.toString()
                )
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            UserAvatar(
                user = streamUser,
                modifier = Modifier.size(58.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User: ${user?.email}",
                style = ChatTheme.typography.bodyBold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onSendMessageClick,
                colors = ButtonDefaults.buttonColors(ChatTheme.colors.primaryAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 90.dp)
            ) {
                Text(text = "Send a message", fontSize = 16.sp)
            }
        }
}