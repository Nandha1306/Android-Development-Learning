# DataStore Theme Preference Practice

This project is a simple Android application built using **Jetpack Compose**, **ViewModel**, and **Jetpack DataStore Preferences**.

The goal is to understand how to persist a **Boolean** value using DataStore by saving a Dark Mode preference and reading it back.

This is the same pattern that will later be used in DriveOps for storing user preferences such as login status, theme settings, onboarding completion, and other application settings.

---

# Objective

Learn:

* What Jetpack DataStore Preferences is
* How to create a `booleanPreferencesKey`
* How to save a Boolean value
* How to read a Boolean value using `Flow`
* How ViewModel observes DataStore
* How Compose automatically updates when preferences change

---

# Project Structure

```text
app
│
├── MainActivity.kt
├── data
│   └── ThemePreferences.kt
└── ui
    ├── ThemeViewModel.kt
    └── ThemeScreen.kt
```

---

# Application Flow

```text
MainActivity
      │
      ▼
ThemeScreen
      │
      ▼
ThemeViewModel
      │
      ├──────────────► saveTheme(true)
      │
      ▼
ThemePreferences
      │
      ▼
DataStore
      │
      ▼
Stores
dark_mode = true
      │
      ▼
getTheme()
      │
      ▼
Flow<Boolean>
      │
      ▼
Compose observes state
      │
      ▼
Displays
true
```

---

# File Responsibilities

## MainActivity

```kotlin
val preferences = ThemePreferences(this)
val viewModel = ThemeViewModel(preferences)

setContent {
    ThemeScreen(viewModel)
}
```

### Role

* Entry point of the application.
* Creates the DataStore helper.
* Creates the ViewModel.
* Displays the Compose UI.

---

## ThemeScreen

```kotlin
val isDarkMode by viewModel.theme.collectAsState()
```

### Role

* Displays the current theme preference.
* Observes the ViewModel state.
* Automatically updates whenever the stored value changes.

---

## ThemeViewModel

```kotlin
class ThemeViewModel(
    private val preferences: ThemePreferences
) : ViewModel()
```

### Role

* Saves the Dark Mode preference.
* Reads the preference from DataStore.
* Exposes the current value as a `StateFlow`.
* Acts as the bridge between DataStore and the UI.

---

## ThemePreferences

```kotlin
class ThemePreferences(
    private val context: Context
)
```

### Role

* Handles all DataStore operations.
* Saves Boolean preferences.
* Reads Boolean preferences.
* Hides DataStore implementation details from the ViewModel.

---

# Important Syntax

## booleanPreferencesKey

```kotlin
private val DARK_MODE_KEY =
    booleanPreferencesKey("dark_mode")
```

### Role

* Creates a key for storing Boolean values.
* Used to uniquely identify the preference inside DataStore.

---

## saveTheme()

```kotlin
suspend fun saveTheme(isDarkMode: Boolean)
```

### Role

* Stores the Boolean value in DataStore.
* Updates the saved preference.

Example

```kotlin
saveTheme(true)
```

---

## getTheme()

```kotlin
fun getTheme(): Flow<Boolean>
```

### Role

* Reads the stored value.
* Returns a `Flow<Boolean>`.
* Emits updates whenever the preference changes.

---

# Execution Flow

## Step 1

Application starts.

↓

MainActivity creates:

* ThemePreferences
* ThemeViewModel

---

## Step 2

ThemeViewModel initializes.

↓

Calls

```kotlin
saveTheme(true)
```

---

## Step 3

DataStore stores

```text
dark_mode = true
```

---

## Step 4

ViewModel starts observing

```kotlin
getTheme()
```

---

## Step 5

DataStore emits

```text
true
```

---

## Step 6

ViewModel updates

```kotlin
_theme.value = true
```

---

## Step 7

Compose observes the updated state.

↓

UI recomposes automatically.

↓

Displays

```text
true
```

---

# Complete Flow Diagram

```text
App Starts
      │
      ▼
ThemeViewModel
      │
      ▼
saveTheme(true)
      │
      ▼
DataStore
      │
      ▼
Stores Boolean
      │
      ▼
getTheme()
      │
      ▼
Flow<Boolean>
      │
      ▼
StateFlow<Boolean>
      │
      ▼
Compose observes
      │
      ▼
UI updates
```

---

# Architecture

```text
             User
               │
               ▼
         ThemeScreen
               │
               ▼
       ThemeViewModel
               │
               ▼
     ThemePreferences
               │
               ▼
         DataStore
               │
               ▼
 Boolean Preference
```

---

# Why Use DataStore?

Without DataStore:

```text
App starts
      │
      ▼
Preference is lost
```

With DataStore:

```text
Save Preference
      │
      ▼
Stored on Device
      │
      ▼
Available after App Restart
```

DataStore provides an asynchronous, type-safe, and modern way to persist application settings.

---

# Key Takeaways

* `booleanPreferencesKey` creates a key for storing Boolean values.
* `saveTheme()` writes data to DataStore.
* `getTheme()` reads data as a `Flow`.
* ViewModel observes DataStore and exposes state to the UI.
* Compose automatically updates when the stored preference changes.
* DataStore is the recommended replacement for SharedPreferences.

---

# Connection to DriveOps

Current practice:

```text
ThemeScreen
      ↓
ThemeViewModel
      ↓
ThemePreferences
      ↓
DataStore
      ↓
Boolean Preference
```

DriveOps usage:

```text
LoginScreen
      ↓
SettingsViewModel
      ↓
PreferenceManager
      ↓
DataStore
      ↓
Login Status
Theme Mode
Remember Me
Onboarding State
User Preferences
```

The only difference is the type of data being stored. In this project, DataStore stores a Boolean representing the Dark Mode preference. In DriveOps, the same pattern can be used to persist login state, theme preferences, onboarding completion, and other user settings while keeping the architecture clean and maintainable.
