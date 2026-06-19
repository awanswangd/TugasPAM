package org.notes.project.ai

/**
 * Untuk iOS, API key bisa di-inject melalui Info.plist atau di-set manual
 * saat development. Disarankan menggunakan xcconfig + Info.plist untuk
 * project production, agar key tidak ter-hardcode di source code.
 */
actual object ApiConfig {
    actual val geminiApiKey: String = "" // TODO: isi via Info.plist / xcconfig saat development
}
