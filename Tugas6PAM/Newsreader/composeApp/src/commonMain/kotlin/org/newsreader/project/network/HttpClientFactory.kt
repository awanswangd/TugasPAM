package org.newsreader.project.network


import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true  // Abaikan field tidak dikenal dari API
                })
            }
            install(Logging) {
                level = LogLevel.BODY  // Ganti ke LogLevel.NONE untuk production
            }
        }
    }
}