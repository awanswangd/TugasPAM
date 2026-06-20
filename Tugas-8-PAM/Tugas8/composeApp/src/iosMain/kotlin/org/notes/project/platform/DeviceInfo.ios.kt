package org.notes.project.platform

import platform.UIKit.UIDevice

actual class DeviceInfo {

    actual fun getDeviceName(): String {
        return UIDevice.currentDevice.name
    }

    actual fun getOsVersion(): String {
        return "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
    }

    actual fun getAppVersion(): String {
        return "1.0.0"
    }
}
