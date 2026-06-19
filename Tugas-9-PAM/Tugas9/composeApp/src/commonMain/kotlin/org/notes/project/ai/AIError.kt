package org.notes.project.ai

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

/**
 * Representasi error yang mungkin terjadi saat memanggil AI API.
 * Membungkus error HTTP/network/parsing menjadi tipe yang lebih
 * mudah ditangani di layer UI (lihat slide 30-31 materi Pertemuan 9).
 */
sealed class AIError(override val message: String) : Exception(message) {
    data class RateLimited(val retryAfter: Int) :
        AIError("Terlalu banyak permintaan. Coba lagi dalam $retryAfter detik.")

    data class Unauthorized(val detail: String) :
        AIError("API key tidak valid atau belum di-set: $detail")

    data class BadRequest(val detail: String) :
        AIError("Permintaan tidak valid: $detail")

    data class ServerError(val detail: String) :
        AIError("Layanan AI sedang bermasalah: $detail")

    data class NetworkError(val detail: String) :
        AIError("Tidak ada koneksi internet: $detail")

    data class ParseError(val detail: String) :
        AIError("Gagal memproses response AI: $detail")

    data class Unknown(val detail: String) :
        AIError("Terjadi kesalahan: $detail")
}

/**
 * Wrapper aman untuk memanggil AI API. Menangkap exception dari Ktor
 * dan mengonversinya menjadi [AIError] yang spesifik, sehingga caller
 * cukup menangani [Result.failure] tanpa perlu tahu detail HTTP.
 */
suspend fun <T> safeAICall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: AIError) {
        // Error sudah dilempar secara eksplisit dalam blok (mis. response kosong),
        // teruskan apa adanya tanpa dibungkus ulang menjadi Unknown.
        Result.failure(e)
    } catch (e: ClientRequestException) {
        when (e.response.status.value) {
            401, 403 -> Result.failure(AIError.Unauthorized(e.message ?: "Unauthorized"))
            400 -> Result.failure(AIError.BadRequest(e.message ?: "Bad request"))
            429 -> {
                val retryAfter = e.response.headers["Retry-After"]?.toIntOrNull() ?: 60
                Result.failure(AIError.RateLimited(retryAfter))
            }
            else -> Result.failure(AIError.Unknown(e.message ?: "Client error"))
        }
    } catch (e: ServerResponseException) {
        Result.failure(AIError.ServerError(e.message ?: "Server error"))
    } catch (e: IOException) {
        Result.failure(AIError.NetworkError(e.message ?: "No internet connection"))
    } catch (e: SerializationException) {
        Result.failure(AIError.ParseError(e.message ?: "Failed to parse response"))
    } catch (e: Exception) {
        Result.failure(AIError.Unknown(e.message ?: "Unexpected error"))
    }
}
