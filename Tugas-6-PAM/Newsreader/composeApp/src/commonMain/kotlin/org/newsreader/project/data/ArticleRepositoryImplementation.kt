package org.newsreader.project.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ArticleRepositoryImplementation(
    private val client: HttpClient
) : ArticleRepository {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    override suspend fun getArticles(): Result<List<Article>> {
        return try {
            val articles: List<Article> = client
                .get("$baseUrl/posts")
                .body()
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val article: Article = client
                .get("$baseUrl/posts/$id")
                .body()
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}