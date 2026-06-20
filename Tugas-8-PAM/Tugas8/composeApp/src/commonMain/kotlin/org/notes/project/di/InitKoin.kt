package org.notes.project.di

import org.koin.core.context.startKoin

/**
 * Inisialisasi Koin untuk platform yang tidak butuh Context (iOS, Desktop/JVM).
 * Android menginisialisasi Koin secara terpisah di MainActivity karena butuh androidContext().
 */
fun initKoin() {
    startKoin {
        modules(appModules)
    }
}
