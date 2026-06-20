package org.notes.project.platform

// Stub untuk Desktop/JVM — sebagian besar desktop (PC) tidak punya baterai,
// dan JVM tidak punya API standar untuk membaca status baterai laptop.
actual class BatteryInfo {
    actual fun getBatteryLevel(): Int = -1       // -1 = tidak tersedia
    actual fun isCharging(): Boolean = false
}
