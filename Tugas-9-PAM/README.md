# README — Tugas Praktikum Pertemuan 9
## Integrasi AI API — Google Gemini & Prompt Engineering

---

## Identitas Mahasiswa

| | |
|---|---|
| **Nama** | M. Hafizurrahman Akbar |
| **NIM** | 123140123 |
| **Mata Kuliah** | IF25-22017 Pengembangan Aplikasi Mobile |
| **Pertemuan** | 9 — Integrasi AI API |
| **Program Studi** | Teknik Informatika — Institut Teknologi Sumatera |
| **Tahun Akademik** | Genap 2025/2026 |
| **Branch GitHub** | `week-9` |

---

## Deskripsi Tugas

Tugas ini melanjutkan aplikasi **Notes App** (Kotlin Multiplatform + Compose Multiplatform) dari pertemuan-pertemuan sebelumnya dengan menambahkan **integrasi AI** menggunakan **Google Gemini API** (`gemini-2.0-flash`, free tier).

Dua fitur AI diimplementasikan:

1. **✍️ AI Note Summarizer** — tombol "Ringkas dengan AI" di layar Add/Edit Note yang merangkum isi catatan menjadi 2–3 kalimat dan menyisipkannya ke awal catatan.
2. **💬 Smart Chatbot Assistant** — layar chat multi-turn (mengingat history percakapan) yang bisa diakses dari ikon robot di Notes List, untuk bertanya seputar pencatatan atau hal umum lainnya.

---

## Fitur yang Diimplementasikan

- ✅ Integrasi **Google Gemini API** (`gemini-2.0-flash`) via Ktor Client multiplatform
- ✅ **Service layer** terpisah: `GeminiService` (single-shot & multi-turn) dan `GeminiChatService` (penyimpanan history percakapan)
- ✅ **AI Repository pattern** (`AIRepository` / `AIRepositoryImpl`) sebagai abstraksi fitur AI dari ViewModel
- ✅ **System prompt** yang well-designed (`SystemPrompts`) untuk persona asisten dan instruksi ringkasan
- ✅ **Error handling** lengkap: sealed class `AIError` (Unauthorized, RateLimited, BadRequest, ServerError, NetworkError, ParseError) + `safeAICall` wrapper
- ✅ **Retry dengan exponential backoff** (`retryWithBackoff`) untuk error transient (rate limit & server error)
- ✅ **UI states**: loading indicator (typing indicator animasi 3 titik), error banner yang bisa ditutup, empty state
- ✅ **Multi-turn conversation**: chatbot mengingat percakapan sebelumnya selama sesi berjalan
- ✅ API key disimpan aman di `local.properties` (tidak pernah commit ke Git), diakses lewat `BuildConfig` (Android) / `expect-actual ApiConfig` (multiplatform)

---

## Arsitektur Fitur AI

```
ChatScreen / AddEditNoteScreen (Compose UI)
        ↓
ChatViewModel / NotesViewModel (StateFlow)
        ↓
AIRepository (interface)
        ↓
AIRepositoryImpl
   ├── GeminiChatService   → multi-turn chat (menyimpan history)
   └── GeminiService       → single-shot generateContent (ringkasan)
        ↓
   safeAICall { } + retryWithBackoff { }   → AIError sealed class
        ↓
   Ktor HttpClient → Gemini API (generateContent endpoint)
```

### Struktur Folder Baru

```
composeApp/src/
├── commonMain/kotlin/org/notes/project/
│   ├── ai/
│   │   ├── ApiConfig.kt              (expect) - API key
│   │   ├── AIError.kt                - sealed class error + safeAICall
│   │   ├── AIRepository.kt           - interface + impl
│   │   ├── GeminiService.kt          - single-shot & multi-turn call
│   │   ├── GeminiChatService.kt      - conversation history
│   │   ├── HttpClientFactory.kt      (expect) - Ktor client per platform
│   │   ├── RetryWithBackoff.kt       - retry helper
│   │   ├── SystemPrompts.kt          - system prompt definitions
│   │   └── model/
│   │       └── GeminiModels.kt       - Request/Response DTOs
│   └── presentation/
│       ├── ChatScreen.kt             - UI chatbot (bubble + typing indicator)
│       ├── ChatViewModel.kt          - state management chat
│       ├── NotesViewModel.kt         - + fitur summarizeCurrentNote()
│       └── AddEditNoteScreen.kt      - + tombol "Ringkas dengan AI"
├── androidMain/kotlin/.../ai/
│   ├── ApiConfig.android.kt          (actual) - dari BuildConfig
│   └── HttpClientFactory.android.kt  (actual) - engine OkHttp/Android
├── iosMain/kotlin/.../ai/
│   ├── ApiConfig.ios.kt              (actual)
│   └── HttpClientFactory.ios.kt      (actual) - engine Darwin
└── jvmMain/kotlin/.../ai/
    ├── ApiConfig.jvm.kt              (actual) - dari env var
    └── HttpClientFactory.jvm.kt      (actual) - engine OkHttp
```

---

## Setup API Key (Wajib sebelum menjalankan)

1. Buka [Google AI Studio](https://aistudio.google.com), sign in dengan akun Google
2. Klik **Get API key → Create API key**
3. Salin `local.properties.example` menjadi **`local.properties`** di root project (`Tugas7/local.properties`)
4. Isi baris berikut di `local.properties`:
   ```properties
   sdk.dir=/path/to/your/Android/sdk
   GEMINI_API_KEY=isi_api_key_kamu_disini
   ```
5. **JANGAN** commit `local.properties` ke Git — file ini sudah ada di `.gitignore`

> Free tier Gemini: 15 requests/menit, 1500 requests/hari — cukup untuk development.

Untuk menjalankan target **Desktop (JVM)**, API key dibaca dari environment variable:
```bash
export GEMINI_API_KEY=isi_api_key_kamu_disini
./gradlew :composeApp:run
```

---

## System Prompt yang Digunakan

**Smart Chatbot Assistant** (`SystemPrompts.NOTES_ASSISTANT`):
> Asisten virtual aplikasi "My Notes" yang membantu pengguna menulis/merapikan catatan dan menjawab pertanyaan umum, selalu dalam Bahasa Indonesia, jawaban ringkas (maks. 150 kata), jujur jika tidak yakin.

**AI Note Summarizer** (`SystemPrompts.summarizeNotePrompt`):
> Instruksi merangkum catatan ke maksimal 3 kalimat, fokus pada poin utama dan tindak lanjut, tanpa menambahkan informasi yang tidak ada di catatan asli.

---

## Error Handling

| Kode | Tipe `AIError` | Penanganan |
|---|---|---|
| 401 / 403 | `Unauthorized` | Tampilkan pesan API key tidak valid |
| 400 | `BadRequest` | Validasi input sebelum kirim |
| 429 | `RateLimited` | Retry otomatis setelah `Retry-After` detik |
| 5xx | `ServerError` | Retry dengan exponential backoff (1s → 2s → 4s, maks 10s) |
| IOException | `NetworkError` | Tampilkan pesan tidak ada koneksi |
| SerializationException | `ParseError` | Tampilkan pesan gagal memproses response |

Semua error ditangkap di layer `safeAICall`, dibungkus `Result<T>`, lalu ditangani di ViewModel dan ditampilkan sebagai error banner yang bisa ditutup oleh pengguna — UI tidak pernah crash akibat kegagalan API.

---

## Dependencies Tambahan

| Library | Versi | Fungsi |
|---|---|---|
| `io.ktor:ktor-client-core` | 2.3.12 | HTTP client multiplatform |
| `io.ktor:ktor-client-content-negotiation` | 2.3.12 | Negosiasi format request/response |
| `io.ktor:ktor-serialization-kotlinx-json` | 2.3.12 | Serialisasi JSON untuk Ktor |
| `io.ktor:ktor-client-logging` | 2.3.12 | Logging request/response |
| `io.ktor:ktor-client-android` / `-darwin` / `-okhttp` | 2.3.12 | Engine HTTP per platform |
| `org.jetbrains.kotlinx:kotlinx-serialization-json` | 1.7.3 | `@Serializable` data class |

---

## Cara Menjalankan Aplikasi

1. Clone repository dan checkout branch:
   ```bash
   git clone <URL_REPOSITORY>
   git checkout week-9
   ```
2. Salin `local.properties.example` → `local.properties`, isi `GEMINI_API_KEY` (lihat bagian Setup di atas)
3. Buka project di Android Studio → **File > Sync Project with Gradle Files**
4. Jalankan di emulator/device Android, atau `./gradlew :composeApp:run` untuk Desktop

---

## Checklist Pengerjaan

- [x] Setup Ktor Client multiplatform (core, content-negotiation, json, logging)
- [x] Setup `kotlinx-serialization` plugin
- [x] Data model `GeminiRequest` / `GeminiResponse` (DTO)
- [x] `ApiConfig` (expect/actual) untuk API key, dibaca dari `local.properties` / env var
- [x] `GeminiService`: single-shot `generateContent()` dan multi-turn `generateContentWithHistory()`
- [x] `GeminiChatService`: menyimpan conversation history
- [x] `AIRepository` + `AIRepositoryImpl`: abstraksi fitur AI
- [x] `SystemPrompts`: system prompt untuk chatbot dan ringkasan
- [x] `AIError` sealed class + `safeAICall` wrapper
- [x] `retryWithBackoff`: retry otomatis untuk error transient
- [x] `ChatViewModel` + `ChatScreen`: UI chatbot dengan bubble & typing indicator
- [x] Integrasi tombol "Ringkas dengan AI" di `AddEditNoteScreen`
- [x] Loading states & error banner di kedua fitur
- [x] Permission `INTERNET` di `AndroidManifest.xml`
- [x] `local.properties.example` sebagai template, `local.properties` di `.gitignore`
- [ ] Push ke GitHub branch: `week-9`
- [ ] Screenshot & video demo

---

## Screenshot Aplikasi

### 1. Notes List — tombol AI Assistant
(masukkan media disini — Screenshot Notes List dengan ikon AI Assistant di top bar)

### 2. AI Note Summarizer
(masukkan media disini — Screenshot sebelum/sesudah klik "Ringkas dengan AI", termasuk kondisi loading)

### 3. Smart Chatbot Assistant
(masukkan media disini — Screenshot percakapan multi-turn dengan chatbot)

### 4. Error Handling
(masukkan media disini — Screenshot error banner, mis. saat API key belum diisi atau tanpa koneksi internet)

---

## Video Demo

Video demo menunjukkan alur:
- Membuka layar Add/Edit Note → menulis catatan → klik "Ringkas dengan AI" → hasil ringkasan tampil
- Membuka Smart Chatbot Assistant dari Notes List → mengirim beberapa pesan berurutan (multi-turn)
- Menghapus history chat
- Simulasi error (mis. tanpa koneksi internet) → error banner muncul tanpa app crash

(masukkan media disini — link atau file video demo)

---

## Rubrik Penilaian

| Kriteria | Bobot | Deskripsi |
|---|---|---|
| AI Integration | 30% | API calls bekerja, service layer rapi |
| Prompt Engineering | 25% | System prompt well-designed, output terstruktur |
| Error Handling | 20% | Menangani error secara graceful, retry logic |
| UI/UX | 15% | Loading states, responsif, feedback jelas |
| Code Quality | 10% | Kode bersih, arsitektur rapi |

**Bonus:** Multi-turn conversation (+5%) — ✅ sudah diimplementasikan pada Smart Chatbot Assistant.

---

## Referensi

- [Google AI Studio](https://aistudio.google.com)
- [Gemini API Documentation](https://ai.google.dev/docs)
- [Ktor Client Documentation](https://ktor.io/docs/client-create-new-application.html)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Prompt Engineering Guide](https://ai.google.dev/docs/prompting_intro)

---

*Institut Teknologi Sumatera — Program Studi Teknik Informatika — TA Genap 2025/2026*
