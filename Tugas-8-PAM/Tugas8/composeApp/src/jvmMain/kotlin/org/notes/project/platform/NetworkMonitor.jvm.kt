package org.notes.project.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.net.InetSocketAddress
import java.net.Socket

actual class NetworkMonitor {

    actual fun isConnected(): Boolean {
        return try {
            Socket().use {
                it.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    // Stub sederhana — tidak observe real-time, cukup nilai awal
    actual fun observeConnectivity(): Flow<Boolean> = flowOf(isConnected())
}
