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

<img width="438" height="972" alt="Screenshot 2026-04-24 184826" src="https://github.com/user-attachments/assets/18fe1d82-609d-4f7b-bbe7-d1491ae69a3f" />

---

### 2. Add / Edit Note Screen
Form untuk menambahkan atau mengedit catatan.

<img width="434" height="970" alt="Screenshot 2026-04-24 184846" src="https://github.com/user-attachments/assets/56266b6e-888f-445e-a678-b5256d2007c6" /> 
---

### 3. Search Notes
Fitur pencarian yang memfilter notes berdasarkan judul atau konten secara real-time.

<img width="434" height="971" alt="Screenshot 2026-04-24 184858" src="https://github.com/user-attachments/assets/5c6bf404-6892-40bd-ae1e-ca9f39e35827" />

---

### 4. Settings Screen
Halaman pengaturan menggunakan DataStore. Tersedia pilihan tema dan sort order.

<img width="455" height="983" alt="Screenshot 2026-04-24 184903" src="https://github.com/user-attachments/assets/66962938-82ad-4a74-98c6-2186464e3c9a" />

---

### 5. Offline Mode
Demonstrasi aplikasi tetap berfungsi saat mode pesawat / tanpa koneksi internet.

<img width="461" height="932" alt="Screenshot 2026-04-24 185614" src="https://github.com/user-attachments/assets/9c0acb49-36da-4f55-88fb-d8c699b196b5" />

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


https://github.com/user-attachments/assets/dba61f60-5c0a-4ca1-9ae5-b6083ae9f6d1

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
