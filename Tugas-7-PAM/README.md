# README — Tugas Praktikum Minggu 7
## Local Data Storage — DataStore & SQLDelight

---

## Identitas Mahasiswa

| | |
|---|---|
| **Nama** | M. Hafizurrahman Akbar |
| **NIM** | 123140123 |
| **Mata Kuliah** | IF25-22017 Pengembangan Aplikasi Mobile |
| **Pertemuan** | 7 — Local Data Storage |
| **Program Studi** | Teknik Informatika — Institut Teknologi Sumatera |
| **Tahun Akademik** | Genap 2025/2026 |
| **Branch GitHub** | `week-7` |

---

## Deskripsi Tugas

Tugas praktikum minggu 7 adalah mengembangkan aplikasi **Notes App** menggunakan Kotlin Multiplatform (KMP) dengan Compose Multiplatform. Aplikasi mengimplementasikan penyimpanan data lokal menggunakan **SQLDelight** untuk database dan **DataStore** (multiplatform-settings) untuk pengaturan/preferences aplikasi.

### Fitur yang Diimplementasikan

- SQLDelight database untuk menyimpan notes secara lokal
- CRUD Operations: Create, Read, Update, Delete notes
- Search functionality untuk mencari notes berdasarkan judul atau konten
- Settings screen dengan DataStore: pilihan tema (light/dark/system) dan sort order
- Offline-first: semua data tersimpan lokal, tersedia tanpa koneksi internet
- UI States yang proper: Loading, Empty, Content

---

## Database Schema

File: `src/commonMain/sqldelight/com/example/notes/db/Note.sq`

```sql
CREATE TABLE Note (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    title      TEXT    NOT NULL,
    content    TEXT    NOT NULL,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL
);

selectAll:
SELECT * FROM Note ORDER BY updated_at DESC;

selectById:
SELECT * FROM Note WHERE id = ?;

insert:
INSERT INTO Note(title, content, created_at, updated_at)
VALUES (?, ?, ?, ?);

update:
UPDATE Note SET title = ?, content = ?, updated_at = ? WHERE id = ?;

delete:
DELETE FROM Note WHERE id = ?;

search:
SELECT * FROM Note WHERE title LIKE ? OR content LIKE ?;
```

---

## Struktur Project

```
composeApp/src/
├── commonMain/
│   ├── kotlin/
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── DatabaseDriverFactory.kt   (expect)
│   │   │   │   └── NoteRepository.kt
│   │   │   └── settings/
│   │   │       └── SettingsManager.kt
│   │   └── ui/
│   │       ├── notes/
│   │       │   ├── NotesListScreen.kt
│   │       │   ├── AddEditNoteScreen.kt
│   │       │   └── NotesViewModel.kt
│   │       └── settings/
│   │           ├── SettingsScreen.kt
│   │           └── SettingsViewModel.kt
│   └── sqldelight/
│       └── com/example/notes/db/
│           └── Note.sq
├── androidMain/
│   └── kotlin/.../DatabaseDriverFactory.android.kt
└── iosMain/
    └── kotlin/.../DatabaseDriverFactory.ios.kt
```

---

## Dependencies

| Library | Versi | Fungsi |
|---|---|---|
| `app.cash.sqldelight` | 2.0.1 | Database SQLite multiplatform |
| `multiplatform-settings` | 1.1.1 | Key-value storage (DataStore KMP) |
| `multiplatform-settings-coroutines` | 1.1.1 | Flow support untuk settings |
| `sqldelight:coroutines-extensions` | 2.0.1 | Flow support untuk SQLDelight |
| `kotlinx-datetime` | 0.5.0 | Timestamp (epoch milliseconds) |

---

## Cara Menjalankan Aplikasi

### Prasyarat

- Android Studio Hedgehog atau lebih baru
- Kotlin 1.9.x atau lebih baru
- Xcode 15+ (untuk build iOS)
- JDK 17

### Langkah-langkah

1. Clone repository:
   ```bash
   git clone <URL_REPOSITORY>
   git checkout week-7
   ```
2. Buka project di Android Studio
3. Jalankan Gradle Sync — **File > Sync Project with Gradle Files**
4. Build dan jalankan di emulator/device pilihan

---

## Screenshot Aplikasi

### 1. Notes List Screen
Menampilkan daftar semua catatan dengan UI states: Loading, Empty, dan Content.

(masukkan media disini — Screenshot Notes List Screen: kondisi loading, empty state, dan list notes)

---

### 2. Add / Edit Note Screen
Form untuk menambahkan atau mengedit catatan.

(masukkan media disini — Screenshot Add Note Screen dan Edit Note Screen)

---

### 3. Search Notes
Fitur pencarian yang memfilter notes berdasarkan judul atau konten secara real-time.

(masukkan media disini — Screenshot Search Notes: hasil pencarian dengan keyword tertentu)

---

### 4. Settings Screen
Halaman pengaturan menggunakan DataStore. Tersedia pilihan tema dan sort order.

(masukkan media disini — Screenshot Settings Screen: toggle tema dan opsi sort order)

---

### 5. Offline Mode
Demonstrasi aplikasi tetap berfungsi saat mode pesawat / tanpa koneksi internet.

(masukkan media disini — Screenshot offline mode: data tetap tampil dari local DB)

---

## Video Demo

Video demo berdurasi kurang dari 45 detik menunjukkan alur berikut:

- Menambahkan note baru (Create)
- Melihat daftar notes (Read)
- Mengedit note yang sudah ada (Update)
- Menghapus note (Delete)
- Mencari note dengan keyword (Search)
- Mengubah tema di Settings
- Membuka aplikasi dalam kondisi offline — data tetap muncul

(masukkan media disini — link atau file video demo, maks. 45 detik)

---

## Rubrik Penilaian

| Komponen | Bobot | Kriteria |
|---|---|---|
| SQLDelight Setup | 20% | Schema, queries, driver setup |
| CRUD Operations | 25% | All operations work correctly |
| DataStore Settings | 15% | Preferences saved and applied |
| Search Feature | 15% | Search works properly |
| UI/UX | 15% | Clean UI, proper states |
| Code Quality | 10% | Clean code, documentation |
| **Bonus** | **+10%** | **Sync dengan remote API** |

> ⚠️ Penalti: Terlambat **-10%/hari** | Plagiat: **nilai 0**

---

## Checklist Pengerjaan

- [ ] Setup SQLDelight plugin di `build.gradle.kts`
- [ ] Menulis schema `Note.sq` dengan 6 queries (selectAll, selectById, insert, update, delete, search)
- [ ] Implementasi `DatabaseDriverFactory` (expect/actual) untuk Android dan iOS
- [ ] Implementasi `NoteRepository` dengan Flow
- [ ] Implementasi `NotesViewModel` dengan StateFlow
- [ ] Setup multiplatform-settings untuk DataStore
- [ ] Implementasi `SettingsManager` (theme + sort order)
- [ ] Implementasi `SettingsViewModel`
- [ ] Notes List Screen dengan UI states (loading, empty, content)
- [ ] Add/Edit Note Screen dengan form input
- [ ] Search functionality
- [ ] Settings Screen dengan pilihan tema dan sort order
- [ ] Push ke GitHub branch: `week-7`
- [ ] README dengan database schema dan screenshot
- [ ] Video demo 45 detik

---

## Referensi

- [SQLDelight Official Documentation](https://cashapp.github.io/sqldelight)
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
- [Android DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Offline-First Apps](https://developer.android.com/topic/architecture/data-layer/offline-first)
- [SQLDelight KMP Tutorial](https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html)
- [Repository Pattern](https://developer.android.com/topic/architecture/data-layer)

---

*Institut Teknologi Sumatera — Program Studi Teknik Informatika — TA Genap 2025/2026*
