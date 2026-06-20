package org.notes.project.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.notes.project.db.DatabaseDriverFactory
import org.notes.project.platform.BatteryInfo
import org.notes.project.platform.DeviceInfo
import org.notes.project.platform.NetworkMonitor

actual val platformModule: Module = module {
    // DatabaseDriverFactory butuh Context di Android
    single { DatabaseDriverFactory(androidContext()) }

    // DeviceInfo & NetworkMonitor
    single { DeviceInfo() }
    single { NetworkMonitor(androidContext()) }

    // Bonus: BatteryInfo
    single { BatteryInfo(androidContext()) }
}
