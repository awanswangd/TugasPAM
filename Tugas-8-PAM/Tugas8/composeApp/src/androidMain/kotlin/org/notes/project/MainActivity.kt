package org.notes.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.notes.project.di.appModules

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Inisialisasi Koin sebelum UI dibuat — semua dependency (database,
        // repository, settings, DeviceInfo, NetworkMonitor) didaftarkan di sini.
        stopKoin() // jaga-jaga kalau Koin sudah pernah start (mis. saat preview/hot reload)
        startKoin {
            androidContext(this@MainActivity.applicationContext)
            modules(appModules)
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
