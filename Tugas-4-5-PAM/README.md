# Notes App - Tugas Pertemuan 5
## Pengembangan Aplikasi Mobile - IF25-22017
**Institut Teknologi Sumatera**

---

## Deskripsi
Aplikasi Notes dengan navigasi multi-screen menggunakan Compose Multiplatform dan Navigation Component.

## Fitur
- ✅ Bottom Navigation (Notes, Favorites, Profile)
- ✅ Note List → Note Detail dengan passing `noteId`
- ✅ Add Note via Floating Action Button
- ✅ Edit Note dengan passing `noteId` sebagai argument
- ✅ Back navigation dari semua screen
- ✅ Navigation Drawer (Bonus)

## Struktur Folder
```
composeApp/src/commonMain/kotlin/com/notesapp/
├── navigation/
│   ├── Screen.kt           # Sealed class routes
│   ├── BottomNavItem.kt    # Bottom nav items
│   └── AppNavigation.kt    # NavHost setup
├── screens/
│   ├── NoteListScreen.kt
│   ├── NoteDetailScreen.kt
│   ├── AddNoteScreen.kt
│   ├── EditNoteScreen.kt
│   ├── FavoritesScreen.kt
│   └── ProfileScreen.kt
├── components/
│   ├── BottomNavBar.kt
│   └── NoteCard.kt
├── model/
│   └── Note.kt
└── MainScreen.kt
```

## Navigation Flow
```
Bottom Navigation Tabs:
[Notes] [Favorites] [Profile]
   ↓
Note List → Note Detail → Edit Note
FAB + → Add Note
```

## Cara Menjalankan
1. Clone repository
2. Buka di Android Studio / IntelliJ IDEA
3. Sync Gradle
4. Run pada emulator/device Android

## Video Demo

https://github.com/user-attachments/assets/940a6488-7c66-4a1d-94c8-223b548ce54f
