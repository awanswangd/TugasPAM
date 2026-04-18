📰 News Feed Simulator (Kotlin Multiplatform)
Aplikasi simulasi News Feed (Berita Terkini) yang dibangun menggunakan Kotlin Multiplatform (KMP) dan Compose Multiplatform. Proyek ini bertujaun untuk mempelajari implementasi Reactive Programming modern menggunakan Coroutines, Flow, dan StateFlow untuk menangani aliran data (streaming data) secara real-time.

✨ Fitur Utama
Aplikasi ini mengimplementasikan 5 fitur reaktif utama:

Real-time Data Stream: Mensimulasikan datangnya berita baru setiap 2 detik secara terus-menerus menggunakan Flow.

Dynamic Filtering: Memfilter aliran berita secara langsung berdasarkan kategori (Teknologi, Olahraga, Bisnis, Hiburan) tanpa menghentikan stream utama.

Data Transformation: Mengubah data mentah (Model) menjadi format presentasi yang ramah pengguna (UI State).

State Management: Melacak dan menyimpan jumlah berita yang telah dibaca secara persisten selama sesi berjalan menggunakan StateFlow.

Asynchronous Operations: Mengambil detail berita secara asinkron dengan jeda jaringan (network latency) yang disimulasikan menggunakan Coroutines (hasilnya dapat dilihat pada Logcat).

🛠️ Teknologi yang Digunakan
Bahasa: Kotlin

UI Framework: Compose Multiplatform

Asynchronous & Reactive: Kotlin Coroutines & Kotlin Flow

Arsitektur: MVVM (Model-View-ViewModel) dengan Unidirectional Data Flow.

📸 Cuplikan Layar (Screenshots)
Berikut adalah antarmuka aplikasi saat dijalankan di Emulator Android beserta output Logcat yang membuktikan proses asinkron berjalan di latar belakang:

<img width="599" height="993" alt="Screenshot 2026-02-19 172208" src="https://github.com/user-attachments/assets/d25ba4d0-e2c8-4179-91c9-4f46c043bd7d" />

<img width="583" height="1043" alt="Screenshot 2026-02-19 172219" src="https://github.com/user-attachments/assets/c8695a98-28df-4065-8414-5c61cd640d25" />

<img width="578" height="1013" alt="Screenshot 2026-02-19 172232" src="https://github.com/user-attachments/assets/4114fd6e-69db-4d97-9688-409032e6d390" />

<img width="596" height="1039" alt="Screenshot 2026-02-19 172239" src="https://github.com/user-attachments/assets/43f39244-a7fb-4b64-84cb-b6c887b6a0de" />


📂 Struktur Proyek KMP
Proyek ini menggunakan struktur Kotlin Multiplatform standar, di mana seluruh business logic dan UI ditempatkan pada commonMain agar dapat dikompilasi ke berbagai platform (Android, iOS, JVM/Desktop).

commonMain/: Berisi logika utama (NewsRepository, NewsViewModel) dan UI (NewsFeedScreen).

androidMain/: Titik masuk (Entry point) khusus untuk sistem operasi Android.

iosMain/: Titik masuk khusus untuk sistem operasi iOS.

jvmMain/: Titik masuk untuk aplikasi Desktop.

🚀 Cara Menjalankan Aplikasi
Clone repositori ini ke mesin lokal Anda.

Buka proyek menggunakan Android Studio (disarankan versi terbaru yang mendukung KMP).

Tunggu hingga proses sinkronisasi Gradle selesai.

Pilih target run di bagian atas (misalnya composeApp untuk Android atau desktop untuk JVM).

Klik tombol Run (Segitiga hijau).
