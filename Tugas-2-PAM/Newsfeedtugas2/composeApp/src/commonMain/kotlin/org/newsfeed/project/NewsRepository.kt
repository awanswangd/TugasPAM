package org.newsfeed.project

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val timestamp: Long
)

class NewsRepository {
    private val categories = listOf("Teknologi", "Olahraga", "Bisnis", "Hiburan")
    private var newsIdCounter = 0

    val newsStream: Flow<News> = flow {
        while (true) {
            delay(2000)
            newsIdCounter++
            emit(
                News(
                    id = newsIdCounter,
                    title = "Berita Terkini #$newsIdCounter",
                    category = categories.random(),
                    timestamp = currentTimeMillis()
                )
            )
        }
    }

    suspend fun fetchNewsDetail(id: Int): String {
        delay(1000)
        return "Ini adalah konten lengkap dan detail untuk berita dengan ID: $id. Berita ini memuat informasi penting..."
    }
}
