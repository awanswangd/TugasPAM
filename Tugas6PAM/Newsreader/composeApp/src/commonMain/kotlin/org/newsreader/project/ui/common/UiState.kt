package org.newsreader.project.ui.common

// Sealed class generik — bisa dipakai untuk semua screen
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// Extension functions untuk kemudahan
fun <T> UiState<T>.isLoading() = this is UiState.Loading
fun <T> UiState<T>.getOrNull(): T? = (this as? UiState.Success)?.data