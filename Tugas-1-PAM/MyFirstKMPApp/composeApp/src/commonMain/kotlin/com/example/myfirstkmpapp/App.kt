package com.example.myfirstkmpapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Saya M. Hafizurrahman Akbar")

            Button(
                onClick = { showContent = !showContent }
            ) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Platform: ${getPlatformName()}")
                    Text("NIM: 123140123")
                }
            }
        }
    }
}
