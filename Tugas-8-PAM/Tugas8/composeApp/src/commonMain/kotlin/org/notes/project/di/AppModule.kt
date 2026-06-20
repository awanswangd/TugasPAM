package org.notes.project.di

import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.notes.project.data.NoteLocalDataSource
import org.notes.project.data.repository.NoteRepositoryImpl
import org.notes.project.data.settings.SettingsManager
import org.notes.project.db.AppDatabase
import org.notes.project.db.DatabaseDriverFactory
import org.notes.project.domain.repository.NoteRepository
import org.notes.project.presentation.NotesViewModel
import org.notes.project.presentation.SettingsViewModel

/**
 * Module yang sama di semua platform: database, repository, settings, dan ViewModel.
 * Dependency platform-specific (DatabaseDriverFactory, DeviceInfo, NetworkMonitor)
 * didefinisikan di [platformModule] (expect/actual per platform).
 */
val commonModule = module {

    // Database — singleton agar hanya ada satu instance di seluruh app
    single<AppDatabase> {
        AppDatabase(get<DatabaseDriverFactory>().createDriver())
    }

    single { NoteLocalDataSource(database = get()) }

    single<NoteRepository> { NoteRepositoryImpl(localDataSource = get()) }

    // Settings storage (multiplatform-settings)
    single<Settings> { Settings() }
    single { SettingsManager(settings = get()) }

    // ViewModels
    viewModelOf(::NotesViewModel)
    viewModelOf(::SettingsViewModel)
}

/**
 * Module platform-specific.
 * - Android: butuh Context → DatabaseDriverFactory(androidContext()), NetworkMonitor(androidContext())
 * - iOS    : no-arg constructor
 * - JVM    : no-arg constructor (in-memory database)
 */
expect val platformModule: Module

/** Gabungan semua module yang didaftarkan saat startKoin() */
val appModules = listOf(commonModule, platformModule)
