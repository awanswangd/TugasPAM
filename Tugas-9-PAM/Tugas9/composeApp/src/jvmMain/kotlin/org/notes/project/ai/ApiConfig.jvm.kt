package org.notes.project.ai

/**
 * Untuk target Desktop (JVM), API key dibaca dari environment variable
 * GEMINI_API_KEY agar tidak perlu di-hardcode di source code.
 *
 * Cara set (contoh di terminal sebelum menjalankan app):
 *   export GEMINI_API_KEY=your_key_here
 */
actual object ApiConfig {
    actual val geminiApiKey: String = System.getenv("GEMINI_API_KEY") ?: ""
}
