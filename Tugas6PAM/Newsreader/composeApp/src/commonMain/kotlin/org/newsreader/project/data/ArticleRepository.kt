package org.newsreader.project.data

interface ArticleRepository {
    suspend fun getArticles(): Result<List<Article>>
    suspend fun getArticleById(id: Int): Result<Article>
}