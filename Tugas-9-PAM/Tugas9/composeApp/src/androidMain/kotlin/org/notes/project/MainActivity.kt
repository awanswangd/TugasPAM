package org.notes.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.notes.project.db.DatabaseDriverFactory // Make sure this is imported

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            // 1. Pass the Android context to the factory here!
            App(databaseDriverFactory = DatabaseDriverFactory(applicationContext))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // 2. For the Compose preview, we can grab the context using LocalContext
    App(databaseDriverFactory = DatabaseDriverFactory(LocalContext.current))
}