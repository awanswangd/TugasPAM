package com.example.notesapp.di

import com.example.notesapp.data.repository.NoteRepositoryImpl
import com.example.notesapp.domain.repository.NoteRepository
import com.example.notesapp.domain.usecase.NoteValidator
import com.example.notesapp.ui.viewmodel.NotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Data layer module: repository dan data sources
val dataModule = module {
    // Singleton repository - satu instance untuk seluruh app
    single<NoteRepository> { NoteRepositoryImpl() }
}

// Domain layer module: use cases dan validators
val domainModule = module {
    // Factory - instance baru setiap dipanggil
    factory { NoteValidator() }
}

// UI/ViewModel layer module
val viewModelModule = module {
    // ViewModel dengan dependency injection
    viewModel { NotesViewModel(get(), get()) }
}

// Gabungan semua modules
val allModules = listOf(dataModule, domainModule, viewModelModule)
