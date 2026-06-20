package org.notes.project.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.notes.project.db.DatabaseDriverFactory
import org.notes.project.platform.BatteryInfo
import org.notes.project.platform.DeviceInfo
import org.notes.project.platform.NetworkMonitor

actual val platformModule: Module = module {
    single { DatabaseDriverFactory() }
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
}
