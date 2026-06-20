package org.notes.project.platform

import java.net.InetAddress

actual class DeviceInfo {

    actual fun getDeviceName(): String {
        return try {
            InetAddress.getLocalHost().hostName
        } catch (e: Exception) {
            "Unknown Desktop"
        }
    }

    actual fun getOsVersion(): String {
        val osName = System.getProperty("os.name") ?: "Unknown OS"
        val osVersion = System.getProperty("os.version") ?: ""
        return "$osName $osVersion"
    }

    actual fun getAppVersion(): String {
        return "1.0.0"
    }
}
