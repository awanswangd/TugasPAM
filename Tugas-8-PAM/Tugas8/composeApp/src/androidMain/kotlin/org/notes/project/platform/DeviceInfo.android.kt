package org.notes.project.platform

import android.os.Build

actual class DeviceInfo {

    actual fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        val model = Build.MODEL
        return if (model.startsWith(manufacturer, ignoreCase = true)) model
        else "$manufacturer $model"
    }

    actual fun getOsVersion(): String {
        return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    }

    actual fun getAppVersion(): String {
        // Hardcoded agar konsisten dengan platform lain tanpa perlu
        // mengaktifkan buildFeatures { buildConfig = true }
        return "1.0.0"
    }
}
