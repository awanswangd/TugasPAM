package com.example.notesapp.di

import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.*

class KoinModuleTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoinIfRunning()
    }

    @Test
    fun `verify all koin modules can be loaded`() {
        koinApplication {
            modules(allModules)
            checkModules()
        }
    }

    @Test
    fun `startKoin initializes successfully`() {
        initKoin()
        // Kalau tidak throw, berarti berhasil
        stopKoinIfRunning()
    }
}
