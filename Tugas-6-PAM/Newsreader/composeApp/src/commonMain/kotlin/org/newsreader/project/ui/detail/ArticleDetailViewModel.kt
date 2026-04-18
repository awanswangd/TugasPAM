package org.newsreader.project.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.newsreader.project.data.Article
import org.newsreader.project.data.ArticleRepository
import org.newsreader.project.ui.common.UiState

class ArticleDetailViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Article>>(UiState.Loading)
    val uiState: StateFlow<UiState<Article>> = _uiState.asStateFlow()

    fun loadArticle(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticleById(id)
                .onSuccess { article ->
                    _uiState.value = UiState.Success(article)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Artikel tidak ditemukan."
                    )
                }
        }
    }
}