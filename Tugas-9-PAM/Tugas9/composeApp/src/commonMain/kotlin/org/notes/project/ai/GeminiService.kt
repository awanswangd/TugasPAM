package org.notes.project.ai

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.notes.project.ai.model.*

/**
 * Service untuk berkomunikasi dengan Google Gemini API.
 * Menggunakan model gemini-2.0-flash (free tier: 15 RPM / 1500 req per hari).
 *
 * Referensi: slide 15-16 materi Pertemuan 9 - Integrasi AI API.
 */
class GeminiService(private val client: HttpClient) {

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.0-flash"

    /**
     * Generate konten dari satu prompt (single-shot, tanpa history).
     * Cocok untuk fitur seperti ringkasan, terjemahan, analisis teks, dll.
     */
    suspend fun generateContent(
        prompt: String,
        systemInstruction: String? = null
    ): Result<String> = safeAICall {
        val fullPrompt = if (systemInstruction != null) {
            "$systemInstruction\n\n$prompt"
        } else {
            prompt
        }

        val request = GeminiRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = fullPrompt)))
            ),
            generationConfig = GenerationConfig(
                temperature = 0.7,
                maxOutputTokens = 1000
            )
        )

        val response: GeminiResponse = client.post(
            "$baseUrl/models/$model:generateContent"
        ) {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }.body()

        response.candidates.firstOrNull()
            ?.content?.parts?.firstOrNull()?.text
            ?: throw AIError.ParseError("Response kosong dari Gemini")
    }

    /**
     * Generate konten dengan history percakapan penuh (multi-turn).
     * Digunakan oleh [GeminiChatService] untuk fitur chatbot.
     */
    suspend fun generateContentWithHistory(
        history: List<Content>
    ): Result<Content> = safeAICall {
        val request = GeminiRequest(
            contents = history,
            generationConfig = GenerationConfig(
                temperature = 0.7,
                maxOutputTokens = 1000
            )
        )

        val response: GeminiResponse = client.post(
            "$baseUrl/models/$model:generateContent"
        ) {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }.body()

        response.candidates.firstOrNull()?.content
            ?: throw AIError.ParseError("Response kosong dari Gemini")
    }
}
