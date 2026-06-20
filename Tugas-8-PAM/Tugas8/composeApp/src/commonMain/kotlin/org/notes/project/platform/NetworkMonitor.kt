package org.notes.project.platform

import kotlinx.coroutines.flow.Flow

/**
 * Memantau status koneksi internet device.
 * Implementasi berbeda per platform — lihat NetworkMonitor.android.kt / NetworkMonitor.ios.kt / NetworkMonitor.jvm.kt
 */
expect class NetworkMonitor {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}
