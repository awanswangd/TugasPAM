package org.newsfeed.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NewsFeedScreen(viewModel: NewsViewModel) {
    val displayList by viewModel.displayNewsFeed.collectAsState()
    val readCount by viewModel.readCount.collectAsState()
    val currentCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("Semua", "Teknologi", "Olahraga", "Bisnis", "Hiburan")

    Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Text(
            text = "Total Dibaca: $readCount berita",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                Button(
                    onClick = { viewModel.setCategory(category) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentCategory == category)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (currentCategory == category)
                            MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(displayList) { news ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            viewModel.readNews(news.id)
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = news.displayTitle, style = MaterialTheme.typography.titleMedium)
                        Text(text = news.timeLabel, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewsFeedScreenPreview() {
    val repository = NewsRepository()
    val viewModel = NewsViewModel(repository)
    
    MaterialTheme {
        Surface {
            NewsFeedScreen(viewModel = viewModel)
        }
    }
}
