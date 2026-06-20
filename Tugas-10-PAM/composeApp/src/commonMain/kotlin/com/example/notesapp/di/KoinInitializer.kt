package com.example.notesapp.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun initKoin() {
    startKoin {
        modules(allModules)
    }
}

fun stopKoinIfRunning() {
    try {
        stopKoin()
    } catch (e: Exception) {
        // Koin not started, ignore
    }
}
