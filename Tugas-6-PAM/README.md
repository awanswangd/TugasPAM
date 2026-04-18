# News Reader App

Aplikasi mobile multiplatform untuk membaca artikel berita, dibangun menggunakan Kotlin Multiplatform dan Compose Multiplatform.

Tugas Praktikum Pertemuan 6 — Pengembangan Aplikasi Mobile  
Institut Teknologi Sumatera | TA Genap 2025/2026

---

## API yang Digunakan

**JSONPlaceholder** — `https://jsonplaceholder.typicode.com`

| Endpoint | Fungsi |
|----------|--------|
| `GET /posts` | Mengambil semua artikel |
| `GET /posts/{id}` | Mengambil detail artikel berdasarkan ID |

JSONPlaceholder dipilih karena gratis, tidak memerlukan API key, dan responsenya sudah berbentuk struktur artikel (`id`, `userId`, `title`, `body`). Gambar artikel menggunakan `https://picsum.photos/seed/{id}/600/300` sebagai placeholder yang konsisten per artikel.

---

## Teknologi

- **Kotlin Multiplatform** — satu codebase untuk Android dan iOS
- **Compose Multiplatform** — UI deklaratif
- **Ktor Client 2.3.7** — HTTP client untuk network request
- **Kotlinx Serialization** — parsing JSON ke data class
- **Coil** — memuat gambar secara async
- **Navigation Compose** — navigasi antar layar
- **ViewModel + StateFlow** — manajemen UI state

---

## Fitur

- Daftar artikel dari REST API
- Detail artikel saat item di-klik
- Pull to refresh untuk memuat ulang data
- Loading state saat data sedang dimuat
- Error state dengan tombol retry saat koneksi gagal
- Repository pattern untuk pemisahan data layer

---

## Struktur Project

```
composeApp/src/commonMain/kotlin/
├── data/
│   ├── Article.kt                  # Data class + @Serializable
│   ├── ArticleRepository.kt        # Interface repository
│   └── ArticleRepositoryImpl.kt    # Implementasi dengan Ktor
├── network/
│   └── HttpClientFactory.kt        # Konfigurasi Ktor Client
├── ui/
│   ├── common/
│   │   └── UiState.kt              # Sealed class Loading/Success/Error
│   ├── list/
│   │   ├── ArticleListScreen.kt
│   │   └── ArticleListViewModel.kt
│   └── detail/
│       ├── ArticleDetailScreen.kt
│       └── ArticleDetailViewModel.kt
└── App.kt                          # NavHost + dependency setup
```

---

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio Hedgehog atau lebih baru
3. Pastikan koneksi internet aktif
4. Jalankan konfigurasi `composeApp` pada emulator atau perangkat Android
5. Tidak diperlukan konfigurasi API key

---

## Screenshot

### Success State
Daftar artikel berhasil dimuat dari JSONPlaceholder API.

<img width="409" height="880" alt="Screenshot 2026-04-18 172804" src="https://github.com/user-attachments/assets/5419c610-4036-472a-93a2-1621c2fcacdc" />

### Error State
Ditampilkan saat tidak ada koneksi internet, disertai tombol "Coba Lagi".

<img width="396" height="857" alt="Screenshot 2026-04-18 172752" src="https://github.com/user-attachments/assets/62187026-6533-489e-9632-5171d6ea6a0c" />

### Detail Artikel
Tampilan lengkap artikel setelah item di-klik dari daftar.

<img width="411" height="886" alt="Screenshot 2026-04-18 172811" src="https://github.com/user-attachments/assets/7f7fc5d4-1687-4824-8a90-283207b95650" />

---

## Cara Mereproduksi Setiap State untuk Demo

| State | Cara Trigger |
|-------|--------------|
| Loading | Buka aplikasi — muncul sebelum data datang |
| Success | Biarkan data termuat dengan koneksi normal |
| Error | Aktifkan Airplane Mode, lalu tekan tombol Retry |
| Pull to Refresh | Tarik layar ke bawah pada daftar artikel |

## Video Demo

https://github.com/user-attachments/assets/e966488b-abdf-4db7-a09c-b178616c0213
