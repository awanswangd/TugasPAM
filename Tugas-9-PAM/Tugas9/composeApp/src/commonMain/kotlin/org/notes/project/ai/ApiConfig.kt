package org.notes.project.ai

/**
 * Konfigurasi API key untuk layanan AI.
 *
 * API key TIDAK pernah di-hardcode di sini. Nilai sebenarnya diambil dari
 * `local.properties` melalui BuildConfig (Android) atau environment-specific
 * implementation (iOS/Desktop), sesuai source set masing-masing platform.
 *
 * PENTING: JANGAN pernah commit API key ke repository!
 */
expect object ApiConfig {
    val geminiApiKey: String
}
