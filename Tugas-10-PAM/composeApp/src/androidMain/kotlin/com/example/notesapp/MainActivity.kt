package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.notesapp.di.initKoin
import com.example.notesapp.ui.screens.notes.NotesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi Koin DI
        initKoin()
        enableEdgeToEdge()
        setContent {
            NotesScreen()
        }
    }
}
