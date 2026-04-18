# 📱 My Profile App

Aplikasi profil pengguna yang dibangun menggunakan **Compose Multiplatform**, berjalan di Android dan Desktop dari satu codebase Kotlin.

> Tugas Praktikum Minggu 3 — IF25-22017 Pengembangan Aplikasi Mobile  
> Program Studi Teknik Informatika, Institut Teknologi Sumatera

---

## ✨ Fitur

- 👤 **Header Profil** — Avatar circular, nama, title, dan tombol Follow/Following
- 📝 **Bio Card** — Deskripsi singkat pengguna
- 📋 **Informasi Kontak** — Email, nomor telepon, dan lokasi
- 🔘 **Action Buttons** — Kirim Pesan dan Bagikan Profil
- 🔄 **Interaktif** — Tombol Follow berubah state secara real-time

---

## 🗂️ Struktur Project

```
MyProfileApp/
├── composeApp/
│   └── src/
│       ├── commonMain/
│       │   └── kotlin/org/profile/project/
│       │       ├── App.kt                 # Entry point & MaterialTheme
│       │       ├── Platform.kt            # expect declaration
│       │       ├── ProfileScreen.kt       # Halaman utama
│       │       ├── ProfileHeader.kt       # Reusable Composable #1
│       │       ├── InfoItem.kt            # Reusable Composable #2
│       │       └── ProfileComponents.kt   # Reusable Composable #3 + models
│       ├── androidMain/
│       │   └── kotlin/org/profile/project/
│       │       └── Platform.android.kt    # actual Android implementation
│       └── iosMain/
│           └── kotlin/org/profile/project/
│               └── Platform.ios.kt        # actual iOS implementation
└── build.gradle.kts
```

---

## 🧩 Composable Functions

Terdapat **3 custom composable reusable** sesuai ketentuan tugas:

### 1. `ProfileHeader`
```kotlin
@Composable
fun ProfileHeader(
    name: String,
    title: String,
    avatarInitials: String,
    photoUrl: String? = null,
    isFollowing: Boolean = false,
    onFollowClick: () -> Unit = {}
)
```
Menampilkan bagian hero profil dengan avatar circular, nama, title, dan tombol Follow.

---

### 2. `InfoItem`
```kotlin
@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    tint: Color = Color.Unspecified
)
```
Satu baris informasi dengan icon, label kategori, dan nilai. Dipakai berulang untuk Email, Phone, dan Location.

---

### 3. `ProfileCard`
```kotlin
@Composable
fun ProfileCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
)
```
Card wrapper generik dengan judul section. Digunakan oleh `BioCard` dan `ContactInfoCard`.

---

## 🎨 Komponen UI yang Digunakan

| Komponen | Lokasi Penggunaan |
|----------|-------------------|
| `Column` | `ProfileScreen`, `ProfileHeader`, `ProfileComponents` |
| `Row` | `InfoItem`, `ActionButtons` |
| `Box` | Avatar circular di `ProfileHeader` (gradient background) |
| `Card` | `ProfileCard` wrapper dengan elevasi |
| `Text` | Nama, bio, label, nilai kontak |
| `Button` | Follow, Kirim Pesan |
| `OutlinedButton` | Following state, Bagikan |
| `Icon` | Email, Phone, LocationOn, Share |
| `Divider` | Pemisah antar `InfoItem` |
| `Surface` | Lingkaran latar icon di `InfoItem` |

---

## My Profile App — Minggu 4 (MVVM + State Management)

Pengembangan lanjutan dari Profile App minggu 3, menambahkan MVVM Pattern, Edit Profile, dan Dark Mode Toggle.

Tugas Praktikum Minggu 4 — IF25-22017 Pengembangan Aplikasi Mobile
Program Studi Teknik Informatika, Institut Teknologi Sumatera

---

## ✨ Fitur Baru (Minggu 4)

FiturDeskripsi🏗️ MVVM PatternProfileViewModel + ProfileUiState memisahkan UI dari business logic✏️ Edit ProfileForm edit nama, title, dan bio dengan validasi🌙 Dark Mode ToggleSwitch dark/light mode yang disimpan di ViewModel🔄 State HoistingProfileTextField sepenuhnya stateless🎞️ Animasi TransisiAnimatedContent saat berpindah antara view dan edit mode

---

## 🗂️ Struktur Project (Diperbarui)
```
composeApp/src/commonMain/kotlin/org/profile/project/
├── App.kt                          # Entry point, theme berubah sesuai dark mode
│
├── data/
│   └── ProfileUiState.kt           # ✅ Data class UI State (MVVM - Model)
│
├── viewmodel/
│   └── ProfileViewModel.kt         # ✅ ViewModel dengan StateFlow (MVVM - ViewModel)
│
└── ui/
    ├── ProfileScreen.kt            # ✅ Screen utama + ProfileView + DarkModeCard
    ├── EditProfileScreen.kt        # ✅ Form edit + ProfileTextField (stateless)
    ├── ProfileHeader.kt            # Reusable header (dari minggu 3)
    ├── InfoItem.kt                 # Reusable info row (dari minggu 3)
    └── ProfileComponents.kt        # ProfileCard, BioCard, ContactInfoCard, dll
```
---

## 🏗️ MVVM Implementation

Data Layer — ProfileUiState
kotlindata class ProfileUiState(
    val name: String = "Budi Santoso",
    val bio: String = "...",
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false,
    // ...
)
ViewModel Layer — ProfileViewModel
kotlinclass ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) { _uiState.update { it.copy(name = newName) } }
    fun saveProfile() { _uiState.update { it.copy(isEditMode = false) } }
    fun toggleDarkMode() { _uiState.update { it.copy(isDarkMode = !it.isDarkMode) } }
}
UI Layer — Observe State
```
kotlin@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    // UI berubah otomatis saat uiState berubah
}
```

---

## 🔄 State Hoisting Pattern
```
ProfileTextField adalah contoh stateless composable dengan state hoisting:
ProfileViewModel (menyimpan state)
       ↓ state (name, bio, title)
   ProfileScreen
       ↓ value + onValueChange
  EditProfileScreen
       ↓ value + onValueChange
  ProfileTextField  ← STATELESS, tidak punya internal state
kotlin// Stateless — menerima state & callback dari luar
@Composable
fun ProfileTextField(
    value: String,              // ← State turun dari ViewModel
    onValueChange: (String) -> Unit,  // ← Event naik ke ViewModel
    ...
)
```
---

## 🌙 Dark Mode

Dark mode state disimpan di ViewModel (survive rotasi layar):
```
kotlin// ViewModel
fun toggleDarkMode() {
    _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
}

// App.kt — theme berubah otomatis
val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
MaterialTheme(colorScheme = colorScheme) { ... }
```
---

## 🛠️ Dependensi Tambahan (build.gradle.kts)
```
kotlincommonMain.dependencies {
    // ... dependensi minggu 3 ...

    // ViewModel untuk KMP
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // StateFlow collectAsState
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}
```
---

## 🛠️ Setup & Menjalankan

### Prasyarat
- Android Studio Hedgehog atau lebih baru
- JDK 17+
- Kotlin 1.9+

### Dependensi (`build.gradle.kts`)
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
        }
    }
}
```

### Menjalankan di Android
```bash
./gradlew :composeApp:assembleDebug
```

### Menjalankan di Desktop
```bash
./gradlew :composeApp:run
```

## 📚 Referensi

- [Compose Multiplatform Docs](https://www.jetbrains.com/compose-multiplatform/)
- [Material 3 Components](https://m3.material.io/components)
- [Coil Image Loading](https://coil-kt.github.io/coil/)
- [State and Jetpack Compose](https://developer.android.com/jetpack/compose/state)

---

## Screenshot Aplikasi

<img src='./Screenshot/Screenshot 2026-03-13 150310.png'>
<img width="426" height="925" alt="Screenshot 2026-03-18 020614" src="https://github.com/user-attachments/assets/ce91fdad-f88c-4c7f-af57-935f58d00e71" />
<img width="424" height="937" alt="Screenshot 2026-03-18 020622" src="https://github.com/user-attachments/assets/4f7c104d-7ae9-4b91-9f56-b52ba012364d" />
<img width="408" height="934" alt="Screenshot 2026-03-18 020630" src="https://github.com/user-attachments/assets/9f0b1365-ed2b-4b61-8710-bc825f92c438" />
<img width="411" height="854" alt="Screenshot 2026-03-18 020658" src="https://github.com/user-attachments/assets/e963690c-3173-4033-89c1-e040a29226f6" />
<img width="409" height="869" alt="Screenshot 2026-03-18 020711" src="https://github.com/user-attachments/assets/8517b077-fc3f-4183-8c84-67da3a21d009" />
