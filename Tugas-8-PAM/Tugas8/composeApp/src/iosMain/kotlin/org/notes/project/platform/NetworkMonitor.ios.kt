package org.notes.project.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// Stub untuk iOS. Implementasi penuh bisa menggunakan NWPathMonitor
// dari framework Network (platform.Network.nw_path_monitor_create dst).
actual class NetworkMonitor {
    actual fun isConnected(): Boolean = true
    actual fun observeConnectivity(): Flow<Boolean> = flowOf(true)
}
