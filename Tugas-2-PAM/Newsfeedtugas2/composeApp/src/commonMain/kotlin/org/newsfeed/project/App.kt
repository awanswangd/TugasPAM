package org.newsfeed.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val repository = remember { NewsRepository() }
    val viewModel = remember { NewsViewModel(repository) }

    MaterialTheme {
        Surface {
            NewsFeedScreen(viewModel)
        }
    }
}
