package org.notes.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.notes.project.ai.AIRepository

/**
 * ViewModel untuk fitur Smart Chatbot Assistant.
 * Lihat slide 25 materi Pertemuan 9 (AI ViewModel).
 */
class ChatViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(message: String) {
        if (message.isBlank() || _uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                messages = it.messages + ChatBubbleMessage(text = message, isUser = true),
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            aiRepository.chat(message)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            messages = it.messages + ChatBubbleMessage(text = response, isUser = false),
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Terjadi kesalahan tidak diketahui"
                        )
                    }
                }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }

    fun clearChat() {
        aiRepository.clearChatHistory()
        _uiState.value = ChatUiState()
    }
}

data class ChatUiState(
    val messages: List<ChatBubbleMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ChatBubbleMessage(
    val text: String,
    val isUser: Boolean
)
