package org.notes.project.platform

/**
 * BONUS: Menyediakan informasi baterai device (level & status charging).
 * Implementasi berbeda per platform — lihat BatteryInfo.android.kt / BatteryInfo.ios.kt / BatteryInfo.jvm.kt
 */
expect class BatteryInfo {
    fun getBatteryLevel(): Int   // 0-100, -1 jika tidak tersedia
    fun isCharging(): Boolean
}
