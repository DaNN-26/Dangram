package com.example.dangram.ui.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorSnackbar(
    updateState: () -> Unit
) {
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 26.dp)
    ) {
        Text(text = "Error! Try to change your email or password.")
    }
    LaunchedEffect(Unit) {
        delay(3000)
        updateState()
    }
}