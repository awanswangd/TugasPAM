package org.notes.project.ai

import io.ktor.client.*
import io.ktor.client.engine.android.*

actual fun createHttpClient(): HttpClient = HttpClient(Android) {
    defaultHttpClientConfig(this)
}
