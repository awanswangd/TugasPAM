package org.notes.project.platform

import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceBatteryStateCharging
import platform.UIKit.UIDeviceBatteryStateFull

actual class BatteryInfo {

    init {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
    }

    actual fun getBatteryLevel(): Int {
        val level = UIDevice.currentDevice.batteryLevel
        return if (level < 0f) -1 else (level * 100).toInt()
    }

    actual fun isCharging(): Boolean {
        val state = UIDevice.currentDevice.batteryState
        return state == UIDeviceBatteryStateCharging || state == UIDeviceBatteryStateFull
    }
}
