package org.newsreader.project.data

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    val imageUrl: String get() = "https://picsum.photos/seed/${id}/400/250"
    val preview: String get() = if (body.length > 100) body.take(100) + "..." else body
}
