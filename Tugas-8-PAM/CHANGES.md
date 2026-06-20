# Perubahan Week 8 — Platform-Specific Features

Project dasar: hasil clone dari `Tugas-8-PAM/Tugas7` (package `org.notes.project`).
Berikut semua perubahan yang ditambahkan untuk memenuhi tugas Week 8.

---

## 1. File BARU

### commonMain
| File | Fungsi |
|---|---|
| `platform/DeviceInfo.kt` | `expect class` — kontrak getDeviceName(), getOsVersion(), getAppVersion() |
| `platform/NetworkMonitor.kt` | `expect class` — kontrak isConnected(), observeConnectivity(): Flow\<Boolean\> |
| `platform/BatteryInfo.kt` | **Bonus** `expect class` — getBatteryLevel(), isCharging() |
| `di/AppModule.kt` | `commonModule` (database, repository, settings, ViewModel) + `expect val platformModule` |
| `di/InitKoin.kt` | `initKoin()` — dipakai iOS & JVM untuk start Koin tanpa Context |

### androidMain
| File | Fungsi |
|---|---|
| `platform/DeviceInfo.android.kt` | actual — pakai `Build.MODEL`, `Build.VERSION` |
| `platform/NetworkMonitor.android.kt` | actual — pakai `ConnectivityManager` + `callbackFlow` |
| `platform/BatteryInfo.android.kt` | actual — pakai `BatteryManager` |
| `di/AppModule.android.kt` | `actual val platformModule` — pakai `androidContext()` |

### iosMain
| File | Fungsi |
|---|---|
| `platform/DeviceInfo.ios.kt` | actual — pakai `UIDevice.currentDevice` |
| `platform/NetworkMonitor.ios.kt` | actual (stub) — selalu return `true` |
| `platform/BatteryInfo.ios.kt` | actual — pakai `UIDevice.batteryLevel` |
| `di/AppModule.ios.kt` | `actual val platformModule` — no-arg constructor |

### jvmMain
| File | Fungsi |
|---|---|
| `platform/DeviceInfo.jvm.kt` | actual — pakai `InetAddress`, system properties |
| `platform/NetworkMonitor.jvm.kt` | actual — cek koneksi socket ke `8.8.8.8:53` |
| `platform/BatteryInfo.jvm.kt` | actual (stub) — desktop umumnya tidak punya baterai |
| `di/AppModule.jvm.kt` | `actual val platformModule` — no-arg constructor |

---

## 2. File yang DIMODIFIKASI

| File | Perubahan |
|---|---|
| `gradle/libs.versions.toml` | + versi `koin` (3.5.3), `koin-compose` (1.1.2), + 5 library entry |
| `composeApp/build.gradle.kts` | + dependency Koin di `commonMain` dan `androidMain` |
| `composeApp/src/androidMain/AndroidManifest.xml` | + permission `ACCESS_NETWORK_STATE` |
| `App.kt` | Hapus instansiasi manual `databaseDriverFactory`/`AppDatabase`/`NoteRepository`. Sekarang `SettingsManager` via `koinInject()`, `NotesViewModel` via `koinViewModel()` |
| `MainActivity.kt` | + `startKoin { androidContext(...); modules(appModules) }` sebelum `setContent` |
| `MainViewController.kt` (iOS) | + `initKoin()` dipanggil sekali sebelum `App()` |
| `main.kt` (JVM) | + `initKoin()` dipanggil di awal `main()` |
| `NotesListScreen.kt` | + composable `NetworkStatusIndicator()` ditampilkan di atas search field |
| `SettingsScreen.kt` | + section "Device Information" di bagian bawah (nama device, OS version, app version) |

---

## 3. Cara kerja arsitektur DI

```
commonModule (sama di semua platform)
├── AppDatabase          ← butuh DatabaseDriverFactory (dari platformModule)
├── NoteLocalDataSource   ← butuh AppDatabase
├── NoteRepository        ← butuh NoteLocalDataSource
├── SettingsManager        ← butuh Settings
├── NotesViewModel          ← butuh NoteRepository
└── SettingsViewModel        ← butuh SettingsManager

platformModule (expect/actual, beda tiap platform)
├── DatabaseDriverFactory   (Android: +Context | iOS/JVM: no-arg)
├── DeviceInfo
├── NetworkMonitor          (Android: +Context | iOS/JVM: no-arg)
└── BatteryInfo (bonus)
```

Tidak ada lagi `NoteRepository(AppDatabase(DatabaseDriverFactory(ctx)))` ditulis manual
di `App.kt` — semua sudah diambil dari Koin container via `koinInject()` / `koinViewModel()`.

---