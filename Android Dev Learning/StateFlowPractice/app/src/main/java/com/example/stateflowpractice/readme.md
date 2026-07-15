# StateFlow Counter Practice

This project is a simple Counter application built using **Jetpack Compose**, **ViewModel**, and **StateFlow**.

The goal is to understand how **StateFlow** works with Compose and how the **ViewModel** becomes the single source of truth for UI state.

This is the same architecture that will later be used in DriveOps for handling login state, loading state, and API responses.

---

# Objective

Learn:

- What `MutableStateFlow` is
- What `StateFlow` is
- Why state should live inside the ViewModel
- How Compose observes StateFlow
- How UI automatically updates (Recomposition)
- How to implement Unidirectional Data Flow (UDF)

---

# Project Structure

```
app
│
├── MainActivity.kt
├── ui
│   └── CounterScreen.kt
└── viewmodel
    └── CounterViewModel.kt
```

---

# Application Flow

```
MainActivity
      │
      ▼
CounterScreen
      │
      ▼
User Clicks Button
      │
      ▼
CounterViewModel
      │
      ▼
Update MutableStateFlow
      │
      ▼
StateFlow emits new value
      │
      ▼
Compose observes state
      │
      ▼
UI Recomposition
      │
      ▼
Updated Counter Display
```

---

# File Responsibilities

## MainActivity

```kotlin
setContent {
    CounterScreen()
}
```

### Role

- Entry point of the application.
- Displays the Compose UI.
- Does not contain business logic.

---

## CounterScreen

```kotlin
val count by viewModel.count.collectAsStateWithLifecycle()
```

### Role

- Displays the current counter value.
- Observes the ViewModel state.
- Sends user actions to the ViewModel.
- Does not modify state directly.

---

## CounterViewModel

```kotlin
class CounterViewModel : ViewModel()
```

### Role

- Holds the counter state.
- Exposes read-only state to the UI.
- Updates state when user actions occur.
- Acts as the single source of truth.

---

# Important Syntax

## MutableStateFlow

```kotlin
private val _count = MutableStateFlow(0)
```

### Role

- Holds mutable state inside the ViewModel.
- Only the ViewModel can update this value.

Example

```kotlin
_count.value++
```

---

## StateFlow

```kotlin
val count = _count.asStateFlow()
```

### Role

- Exposes a read-only version of the state.
- Prevents the UI from changing the state directly.

Think of it as:

```
ViewModel
    │
MutableStateFlow
    │
Read-only StateFlow
    │
UI
```

---

## collectAsStateWithLifecycle()

```kotlin
val count by viewModel.count.collectAsStateWithLifecycle()
```

### Role

- Collects values emitted by StateFlow.
- Converts them into Compose State.
- Automatically updates the UI whenever the value changes.
- Stops collecting when the screen is inactive.

---

## Increment

```kotlin
fun increment() {
    _count.value++
}
```

### Role

- Increases the counter by one.
- Emits the updated value through StateFlow.

---

## Decrement

```kotlin
fun decrement() {
    _count.value--
}
```

### Role

- Decreases the counter by one.
- Emits the updated value.

---

## Reset

```kotlin
fun reset() {
    _count.value = 0
}
```

### Role

- Resets the counter to zero.
- Emits the updated value.

---

# UI Events

Instead of changing the state directly:

❌ Don't do this

```kotlin
count++
```

Instead, the UI simply notifies the ViewModel.

```kotlin
Button(
    onClick = {
        viewModel.increment()
    }
)
```

Similarly,

```kotlin
viewModel.decrement()
```

and

```kotlin
viewModel.reset()
```

The UI sends events.

The ViewModel decides how the state should change.

---

# Execution Flow

## Step 1

Application starts.

↓

MainActivity loads

```
CounterScreen()
```

---

## Step 2

CounterScreen observes

```kotlin
viewModel.count
```

Current value

```
0
```

UI displays

```
Count: 0
```

---

## Step 3

User presses

```
Increment
```

↓

Calls

```kotlin
viewModel.increment()
```

---

## Step 4

ViewModel updates

```kotlin
_count.value++
```

New value

```
1
```

---

## Step 5

StateFlow emits the new value.

---

## Step 6

Compose detects the change.

↓

Recomposition occurs.

↓

UI displays

```
Count: 1
```

---

## Step 7

User presses

```
Decrement
```

↓

Calls

```kotlin
viewModel.decrement()
```

↓

State changes.

↓

Compose recomposes.

---

## Step 8

User presses

```
Reset
```

↓

Calls

```kotlin
viewModel.reset()
```

↓

Counter becomes

```
0
```

↓

Compose recomposes.

---

# Complete Flow Diagram

```
App Starts
      │
      ▼
CounterScreen
      │
      ▼
collectAsStateWithLifecycle()
      │
      ▼
Displays Count
      │
      ▼
User clicks Button
      │
      ▼
ViewModel Function
      │
      ▼
MutableStateFlow Updated
      │
      ▼
StateFlow Emits
      │
      ▼
Compose Observes
      │
      ▼
Recomposition
      │
      ▼
Updated Counter Display
```

---

# Architecture

```
             User
               │
               ▼
        CounterScreen
               │
               ▼
      CounterViewModel
               │
               ▼
 MutableStateFlow<Int>
               │
               ▼
     StateFlow<Int>
               │
               ▼
collectAsStateWithLifecycle()
               │
               ▼
      Compose Recomposition
               │
               ▼
       Updated Counter UI
```

---

# Unidirectional Data Flow (UDF)

This project follows **Unidirectional Data Flow**, where data moves in one direction only.

```
User Action
      │
      ▼
Composable
      │
      ▼
ViewModel Function
      │
      ▼
Update MutableStateFlow
      │
      ▼
StateFlow Emits
      │
      ▼
Composable Observes
      │
      ▼
UI Updates
```

The UI never changes the state directly.

The ViewModel owns the state.

---

# Key Takeaways

- `ViewModel` owns the application state.
- `MutableStateFlow` stores mutable data.
- `StateFlow` exposes read-only state to the UI.
- `collectAsStateWithLifecycle()` observes StateFlow safely.
- UI never changes state directly.
- UI only sends user actions.
- Compose automatically recomposes when StateFlow emits a new value.
- This follows the Single Source of Truth (SSOT) and Unidirectional Data Flow (UDF) principles.

---

# Connection to DriveOps

Current practice:

```
Button
    ↓
ViewModel.increment()
    ↓
MutableStateFlow
    ↓
StateFlow
    ↓
Compose UI
```

DriveOps login flow:

```
Login Button
      ↓
LoginScreen
      ↓
LoginViewModel.login()
      ↓
Update LoginUiState
      ↓
StateFlow<LoginUiState>
      ↓
Compose observes state
      ↓
UI updates automatically
```

The only difference is the type of state being managed. In this project it's an integer counter, while in DriveOps it will be loading states, authentication states, user information, and API responses. The overall architecture and data flow remain the same.