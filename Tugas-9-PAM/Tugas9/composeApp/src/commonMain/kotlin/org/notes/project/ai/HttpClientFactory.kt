package org.notes.project.ai

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Membuat [HttpClient] Ktor dengan konfigurasi JSON yang lentur terhadap
 * field tak dikenal (`ignoreUnknownKeys`), sesuai praktik pada slide 10
 * materi Pertemuan 9. Engine HTTP konkret disediakan oleh masing-masing
 * platform (expect/actual), supaya commonMain tidak bergantung pada
 * implementasi engine tertentu.
 */
expect fun createHttpClient(): HttpClient

internal fun defaultHttpClientConfig(client: HttpClientConfig<*>) {
    client.apply {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }
}
