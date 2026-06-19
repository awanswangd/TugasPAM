package org.notes.project.ai

/**
 * Abstraksi untuk fitur-fitur AI yang dipakai di aplikasi.
 * Lihat slide 24 materi Pertemuan 9 (AI Repository pattern).
 */
interface AIRepository {
    suspend fun chat(message: String): Result<String>
    suspend fun summarizeNote(title: String, content: String): Result<String>
    fun clearChatHistory()
}

class AIRepositoryImpl(
    private val geminiService: GeminiService,
    private val chatService: GeminiChatService = GeminiChatService(geminiService)
) : AIRepository {

    override suspend fun chat(message: String): Result<String> {
        return retryWithBackoff { chatService.sendMessage(message) }
    }

    override suspend fun summarizeNote(title: String, content: String): Result<String> {
        if (content.isBlank()) {
            return Result.failure(AIError.BadRequest("Catatan masih kosong, tulis sesuatu dulu sebelum diringkas."))
        }
        val prompt = SystemPrompts.summarizeNotePrompt(title.ifBlank { "(Tanpa judul)" }, content)
        return retryWithBackoff { geminiService.generateContent(prompt) }
    }

    override fun clearChatHistory() {
        chatService.clearHistory()
    }
}
