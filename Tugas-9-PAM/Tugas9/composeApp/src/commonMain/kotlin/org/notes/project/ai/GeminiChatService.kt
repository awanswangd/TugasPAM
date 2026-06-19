package org.notes.project.ai

import org.notes.project.ai.model.Content
import org.notes.project.ai.model.Part

/**
 * Service untuk percakapan multi-turn dengan Gemini, menyimpan
 * conversationHistory agar AI mengingat konteks pesan sebelumnya.
 *
 * Referensi: slide 16 materi Pertemuan 9 (Multi-turn Conversation).
 */
class GeminiChatService(
    private val geminiService: GeminiService,
    systemPrompt: String = SystemPrompts.NOTES_ASSISTANT
) {

    private val conversationHistory = mutableListOf<Content>()

    init {
        // Inject system prompt sebagai instruksi awal dari "user",
        // diikuti konfirmasi singkat dari "model" agar persona terbentuk
        // sejak giliran pertama (pola umum untuk Gemini generateContent API).
        conversationHistory.add(Content(parts = listOf(Part(text = systemPrompt)), role = "user"))
        conversationHistory.add(
            Content(
                parts = listOf(Part(text = "Baik, saya siap membantu sebagai asisten Notes App.")),
                role = "model"
            )
        )
    }

    suspend fun sendMessage(userMessage: String): Result<String> {
        conversationHistory.add(Content(parts = listOf(Part(text = userMessage)), role = "user"))

        val result = geminiService.generateContentWithHistory(conversationHistory.toList())

        return result.fold(
            onSuccess = { assistantContent ->
                conversationHistory.add(assistantContent)
                val text = assistantContent.parts.firstOrNull()?.text.orEmpty()
                Result.success(text)
            },
            onFailure = { error ->
                // Jangan simpan pesan user ke history kalau gagal, agar bisa di-retry bersih
                conversationHistory.removeAt(conversationHistory.lastIndex)
                Result.failure(error)
            }
        )
    }

    fun clearHistory(systemPrompt: String = SystemPrompts.NOTES_ASSISTANT) {
        conversationHistory.clear()
        conversationHistory.add(Content(parts = listOf(Part(text = systemPrompt)), role = "user"))
        conversationHistory.add(
            Content(
                parts = listOf(Part(text = "Baik, saya siap membantu sebagai asisten Notes App.")),
                role = "model"
            )
        )
    }
}
