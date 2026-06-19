package org.notes.project.ai

import kotlinx.coroutines.delay

/**
 * Retry otomatis dengan exponential backoff untuk error yang bersifat
 * transient (rate limit, server error). Lihat slide 32 materi Pertemuan 9.
 *
 * Mengembalikan [Result] alih-alih melempar exception, agar konsisten
 * dengan pola Result yang dipakai di seluruh layer AI pada project ini.
 */
suspend fun <T> retryWithBackoff(
    times: Int = 3,
    initialDelay: Long = 1000,
    maxDelay: Long = 10000,
    factor: Double = 2.0,
    block: suspend () -> Result<T>
): Result<T> {
    var currentDelay = initialDelay
    repeat(times - 1) {
        val result = block()
        if (result.isSuccess) return result

        when (val error = result.exceptionOrNull()) {
            is AIError.RateLimited -> delay(error.retryAfter * 1000L)
            is AIError.ServerError, is AIError.NetworkError -> {
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
            }
            else -> return result // error permanen (mis. Unauthorized), tidak perlu retry
        }
    }
    return block() // percobaan terakhir
}
