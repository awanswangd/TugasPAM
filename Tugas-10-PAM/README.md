# Tugas Praktikum Minggu 10 - Testing dan Dependency Injection

**Mata Kuliah:** Pengembangan Aplikasi Mobile (IF25-22017)  
**Nama:** M. Hafizurrahman Akbar  
**NIM:** 123140123  
**Branch:** week-10

---

## Deskripsi

Implementasi Dependency Injection (Koin) dan Testing (Unit Test, Flow Test, UI Test) untuk Notes App berbasis Kotlin Multiplatform (KMP).

---

## Struktur Project

```
composeApp/src/
├── commonMain/kotlin/com/example/notesapp/
│   ├── data/
│   │   └── repository/
│   │       └── NoteRepositoryImpl.kt       # Implementasi repository (in-memory)
│   ├── domain/
│   │   ├── model/
│   │   │   └── Note.kt                     # Data model
│   │   ├── repository/
│   │   │   └── NoteRepository.kt           # Interface repository
│   │   └── usecase/
│   │       └── NoteValidator.kt            # Validasi note
│   ├── di/
│   │   ├── AppModule.kt                    # Koin modules (dataModule, domainModule, viewModelModule)
│   │   └── KoinInitializer.kt              # initKoin() function
│   ├── ui/
│   │   ├── screens/notes/
│   │   │   └── NotesScreen.kt              # Composable dengan TestTags
│   │   └── viewmodel/
│   │       ├── NotesUiState.kt             # UI state sealed class
│   │       └── NotesViewModel.kt           # ViewModel dengan DI
│   └── util/
│       └── TestTags.kt                     # Konstanta test tags
├── androidMain/
│   └── MainActivity.kt                     # initKoin() dipanggil di onCreate
├── commonTest/kotlin/com/example/notesapp/
│   ├── NoteValidatorTest.kt                # Unit test validator (9 test cases)
│   ├── NoteRepositoryTest.kt               # Unit test repository (10 test cases)
│   ├── NoteRepositoryFlowTest.kt           # Flow test dengan Turbine (4 test cases)
│   ├── NotesViewModelTest.kt               # ViewModel test dengan MockK (8 test cases)
│   └── KoinModuleTest.kt                   # Koin module verification
└── androidUnitTest/kotlin/
    └── NotesScreenTest.kt                  # UI test Compose (7 test cases)
```

---

## Implementasi Koin DI

### Modules yang dibuat:

```kotlin
// dataModule - Data layer
val dataModule = module {
    single<NoteRepository> { NoteRepositoryImpl() }
}

// domainModule - Domain layer
val domainModule = module {
    factory { NoteValidator() }
}

// viewModelModule - UI layer
val viewModelModule = module {
    viewModel { NotesViewModel(get(), get()) }
}
```

### Cara inisialisasi (MainActivity.kt):
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initKoin()
    // ...
}
```

---

## Daftar Test Cases

### NoteValidatorTest (9 test cases)
| No | Test | Expected |
|----|------|----------|
| 1 | valid note with title and content | returns true |
| 2 | empty title | returns false |
| 3 | blank title (spaces) | returns false |
| 4 | title exactly at limit (200 chars) | returns true |
| 5 | title exceeding limit (201 chars) | returns false |
| 6 | valid note with empty content | returns true |
| 7 | validate throws for empty title | throws ValidationException |
| 8 | validate throws for title too long | throws ValidationException |
| 9 | validate does not throw for valid note | no exception |

### NoteRepositoryTest (10 test cases)
| No | Test | Expected |
|----|------|----------|
| 1 | getAllNotes initially | emits empty list |
| 2 | insertNote | adds note |
| 3 | insertNote | returns assigned id |
| 4 | insertNote multiple | assigns unique ids |
| 5 | getNoteById | returns correct note |
| 6 | getNoteById non-existent | returns null |
| 7 | updateNote | modifies existing note |
| 8 | deleteNote | removes from list |
| 9 | deleteAllNotes | clears all notes |
| 10 | getAllNotes after insert | emits updated list |

### NoteRepositoryFlowTest (4 test cases - Turbine)
| No | Test |
|----|------|
| 1 | emits empty then updates on insert |
| 2 | emits updated list after delete |
| 3 | emits multiple times on multiple inserts |
| 4 | emits empty after deleteAll |

### NotesViewModelTest (8 test cases - MockK)
| No | Test |
|----|------|
| 1 | initial state transitions from loading to success |
| 2 | uiState emits success with notes from repository |
| 3 | addNote calls repository insertNote |
| 4 | addNote with empty title does not call repository |
| 5 | deleteNote calls repository with correct id |
| 6 | updateNote calls repository updateNote |
| 7 | deleteAllNotes calls repository |
| 8 | error from repository emits error state |

### NotesScreenTest (7 test cases - Compose UI Test)
| No | Test |
|----|------|
| 1 | empty state shows message when no notes |
| 2 | notes list displayed when notes exist |
| 3 | add button displayed and clickable |
| 4 | typing in title input updates text field |
| 5 | clicking add button calls onAddNote |
| 6 | loading indicator shown when loading |
| 7 | error message shown when error |

---

## Cara Menjalankan Test

```bash
# Unit tests (commonTest)
./gradlew :composeApp:allTests

# Android instrumented tests (UI test)
./gradlew :composeApp:connectedAndroidTest

# Specific test class
./gradlew :composeApp:testDebugUnitTest --tests "*.NoteValidatorTest"
```

---

## Dependencies yang ditambahkan

```kotlin
// Koin DI
implementation("io.insert-koin:koin-core:3.5.3")
implementation("io.insert-koin:koin-compose:1.1.2")
implementation("io.insert-koin:koin-compose-viewmodel:1.1.2")

// Testing
implementation(kotlin("test"))
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
implementation("app.cash.turbine:turbine:1.0.0")  // Flow testing
implementation("io.mockk:mockk:1.13.9")             // Mocking (JVM)
implementation("io.insert-koin:koin-test:3.5.3")   // Koin testing
```
