# Coroutine + ViewModel Practice

This project is a small practice app to understand how **ViewModel**, **Coroutines**, and **StateFlow** work together in Jetpack Compose.

The goal is to learn the same pattern that will later be used for login requests, API calls, and database operations in DriveOps.

## Objective

Learn:
- What `ViewModel` does
- What `viewModelScope` is
- What a `suspend` function is
- How `StateFlow` manages UI state
- How Compose observes state changes
- Why UI should only observe state

---

## Project Structure

```
app
│
├── MainActivity.kt
├── ui
│   └── LoadingScreen.kt
└── viewmodel
    └── LoadingViewModel.kt
```

---

## Application Flow

```
MainActivity
      │
      ▼
LoadingScreen
      │
      ▼
User Clicks "Load Data"
      │
      ▼
LoadingViewModel
      │
      ▼
viewModelScope.launch
      │
      ▼
fetchData()
      │
      ▼
delay(3000)
      │
      ▼
Update StateFlow
      │
      ▼
Compose Recomposition
      │
      ▼
Display "Data Loaded"
```

---

# File Responsibilities

## MainActivity

```kotlin
setContent {
    LoadingScreen()
}
```

### Role

- Entry point of the application.
- Displays the Compose UI.
- Does not contain business logic.

---

## LoadingScreen

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

### Role

- Displays UI.
- Observes the ViewModel state.
- Sends user actions to the ViewModel.
- Contains no business logic.

---

## LoadingViewModel

```kotlin
class LoadingViewModel : ViewModel()
```

### Role

- Holds UI state.
- Handles business logic.
- Starts coroutines.
- Calls suspend functions.
- Updates StateFlow.

---

# Important Syntax

## MutableStateFlow

```kotlin
private val _uiState = MutableStateFlow("Press the button")
```

### Role

- Holds mutable state inside the ViewModel.
- Only the ViewModel can modify it.

---

## StateFlow

```kotlin
val uiState: StateFlow<String> = _uiState
```

### Role

- Read-only version of the state.
- Exposed to the UI.
- Prevents the UI from modifying data.

---

## viewModelScope.launch

```kotlin
viewModelScope.launch {

}
```

### Role

- Starts a coroutine.
- Runs asynchronous work.
- Automatically cancels when the ViewModel is destroyed.

Think of it as:

```
Start background work safely.
```

---

## suspend Function

```kotlin
private suspend fun fetchData() {
    delay(3000)
}
```

### Role

- Represents long-running work.
- Can call other suspend functions.
- Can pause without blocking the thread.

---

## delay()

```kotlin
delay(3000)
```

### Role

- Suspends the coroutine for 3 seconds.
- Does **not** block the main thread.
- UI remains responsive.

---

## collectAsStateWithLifecycle()

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

### Role

- Observes the StateFlow.
- Converts it into Compose State.
- Automatically updates the UI whenever the value changes.
- Stops collecting when the screen is not active.

---

## Button Click

```kotlin
Button(
    onClick = {
        viewModel.loadData()
    }
)
```

### Role

- Sends a user action to the ViewModel.
- UI does not perform the work itself.

---

# Execution Flow

## Step 1

Application starts.

↓

MainActivity loads

```
LoadingScreen()
```

---

## Step 2

LoadingScreen observes

```kotlin
viewModel.uiState
```

Current value

```
Press the button
```

UI displays

```
Press the button
```

---

## Step 3

User presses

```
Load Data
```

↓

Calls

```kotlin
viewModel.loadData()
```

---

## Step 4

ViewModel starts a coroutine

```kotlin
viewModelScope.launch
```

---

## Step 5

State updates

```kotlin
_uiState.value = "Loading..."
```

StateFlow emits a new value.

Compose automatically recomposes.

UI becomes

```
Loading...
```

---

## Step 6

Suspend function executes

```kotlin
fetchData()
```

↓

```kotlin
delay(3000)
```

Coroutine waits for 3 seconds.

The UI remains responsive because the thread is not blocked.

---

## Step 7

After delay

```kotlin
_uiState.value = "Data Loaded"
```

StateFlow emits another value.

Compose recomposes again.

UI displays

```
Data Loaded
```

---

# Complete Flow Diagram

```
App Starts
      │
      ▼
LoadingScreen
      │
      ▼
collectAsStateWithLifecycle()
      │
      ▼
Shows "Press the button"
      │
      ▼
User taps Load Data
      │
      ▼
viewModel.loadData()
      │
      ▼
viewModelScope.launch
      │
      ▼
_uiState = "Loading..."
      │
      ▼
UI recomposes
      │
      ▼
Shows Loading...
      │
      ▼
fetchData()
      │
      ▼
delay(3000)
      │
      ▼
_uiState = "Data Loaded"
      │
      ▼
UI recomposes
      │
      ▼
Shows Data Loaded
```

---

# Architecture

```
             User
               │
               ▼
        LoadingScreen
               │
               ▼
      LoadingViewModel
               │
               ▼
      viewModelScope.launch
               │
               ▼
      suspend fetchData()
               │
               ▼
         delay(3000)
               │
               ▼
      Update StateFlow
               │
               ▼
     Compose observes state
               │
               ▼
        UI Recomposition
```

---

# Key Takeaways

- `ViewModel` stores and manages UI state.
- `MutableStateFlow` is mutable and stays inside the ViewModel.
- `StateFlow` is exposed to the UI as read-only.
- `viewModelScope.launch` starts lifecycle-aware coroutines.
- `suspend` functions perform asynchronous work.
- `delay()` suspends a coroutine without blocking the UI thread.
- `collectAsStateWithLifecycle()` keeps the UI synchronized with StateFlow.
- The UI only observes state and sends user actions.
- Business logic belongs inside the ViewModel.

---

# Connection to DriveOps

This practice simulates a fake API call using `delay(3000)`.

Current flow:

```
Button
    ↓
ViewModel
    ↓
delay(3000)
    ↓
Update State
    ↓
UI
```

DriveOps login flow:

```
Login Button
      ↓
LoginScreen
      ↓
LoginViewModel
      ↓
LoginUseCase
      ↓
AuthRepository
      ↓
Retrofit API
      ↓
Server Response
      ↓
Update StateFlow
      ↓
Compose Recomposition
```

The only difference is that `delay(3000)` will be replaced by a real network request. The architecture and data flow remain the same.