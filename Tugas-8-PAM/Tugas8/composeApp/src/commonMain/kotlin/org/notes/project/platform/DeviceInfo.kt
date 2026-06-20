package org.notes.project.platform

/**
 * Menyediakan informasi perangkat (nama device, versi OS, versi app).
 * Implementasi berbeda per platform — lihat DeviceInfo.android.kt / DeviceInfo.ios.kt / DeviceInfo.jvm.kt
 */
expect class DeviceInfo {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getAppVersion(): String
}
