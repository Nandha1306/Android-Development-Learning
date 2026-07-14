# Navigation Compose Practice

This project is a small Navigation Compose playground created to understand the fundamentals of navigation before implementing it in DriveOps.

## Objective

Learn:
- What `NavController` is
- What `NavHost` is
- How routes connect screens
- How navigation happens
- How the back stack works

## Project Structure

```
app
│
├── MainActivity.kt
├── navigation
│   ├── AppDestinations.kt
│   └── AppNavHost.kt
└── screens
    ├── HomeScreen.kt
    ├── DetailsScreen.kt
    └── ProfileScreen.kt
```

## Navigation Flow

```
MainActivity
      │
      ▼
AppNavHost
      │
      ▼
NavHost
      │
      ▼
HomeScreen
      │
      ▼
navigate("details")
      │
      ▼
DetailsScreen
      │
      ▼
navigate("profile")
      │
      ▼
ProfileScreen
      │
      ▼
popBackStack()
      │
      ▼
DetailsScreen
```

## File Responsibilities

### MainActivity

```kotlin
setContent {
    AppNavHost()
}
```

**Role**
- Entry point of the application.
- Loads the navigation graph instead of a specific screen.

---

### AppDestinations

```kotlin
object AppDestinations {
    const val HOME = "home"
    const val DETAILS = "details"
    const val PROFILE = "profile"
}
```

**Role**
- Stores all navigation routes.
- Avoids hardcoded strings.
- Acts as the single source of truth for route names.

---

### AppNavHost

```kotlin
val navController = rememberNavController()

NavHost(
    navController = navController,
    startDestination = AppDestinations.HOME
)
```

**Role**
- Creates the navigation graph.
- Defines the start destination.
- Connects every route with its corresponding screen.

---

## Navigation Syntax

### `rememberNavController()`

```kotlin
val navController = rememberNavController()
```

**Role**
- Creates a `NavController`.
- Responsible for all navigation actions.
- Executes `navigate()`, `popBackStack()`, and `navigateUp()`.

---

### `NavHost`

```kotlin
NavHost(
    navController = navController,
    startDestination = AppDestinations.HOME
)
```

**Role**
- Container that displays the current destination.
- Listens for route changes.
- Replaces the displayed screen automatically.

---

### `composable()`

```kotlin
composable(AppDestinations.HOME) {
    HomeScreen(...)
}
```

**Role**
- Registers a destination.
- Maps a route to a composable screen.

Example:

```
Route "home"
      ↓
HomeScreen()
```

---

### `navigate()`

```kotlin
navController.navigate(AppDestinations.DETAILS)
```

**Role**
- Navigates to another screen.
- Pushes the destination onto the navigation back stack.

Before

```
Home
```

After

```
Home
Details
```

---

### `popBackStack()`

```kotlin
navController.popBackStack()
```

**Role**
- Removes the current screen from the back stack.
- Returns to the previous destination.

Before

```
Home
Details
Profile
```

After

```
Home
Details
```

---

## Why Callbacks?

Instead of writing

```kotlin
navController.navigate(...)
```

inside the screen, we pass callbacks.

Example:

```kotlin
HomeScreen(
    onNavigateToDetails = {
        navController.navigate(AppDestinations.DETAILS)
    }
)
```

Inside the screen:

```kotlin
Button(
    onClick = onNavigateToDetails
)
```

**Benefits**
- UI stays independent of navigation.
- Navigation logic remains centralized.
- Screens become reusable and easier to test.

---

## Back Stack

Navigation Compose maintains a stack of destinations.

App Launch

```
Home
```

↓

Navigate

```
Home
Details
```

↓

Navigate

```
Home
Details
Profile
```

↓

Back

```
Home
Details
```

↓

Back

```
Home
```

↓

Back

```
Application closes
```

---

## Complete Execution Flow

1. Application starts.
2. `MainActivity` loads `AppNavHost()`.
3. `AppNavHost` creates a `NavController`.
4. `NavHost` starts with the `home` route.
5. `HomeScreen` is displayed.
6. User taps **Go to Details**.
7. `navController.navigate("details")` is executed.
8. Current route becomes `details`.
9. `NavHost` displays `DetailsScreen`.
10. User taps **Go to Profile**.
11. `navController.navigate("profile")` is executed.
12. Current route becomes `profile`.
13. `NavHost` displays `ProfileScreen`.
14. User taps **Back**.
15. `popBackStack()` removes `ProfileScreen`.
16. `NavHost` automatically displays `DetailsScreen`.

---

## Key Takeaways

- `MainActivity` hosts the entire Compose application.
- `NavController` performs navigation actions.
- `NavHost` displays the destination matching the current route.
- `composable()` registers destinations.
- Routes are unique string identifiers.
- `navigate()` moves forward.
- `popBackStack()` moves backward.
- Navigation decisions belong in `AppNavHost`, not inside UI screens.

---

## Connection to DriveOps

This practice uses:

```
home
details
profile
```

DriveOps will use the same architecture with different destinations:

```
Splash
    ↓
Login
    ↓
Dashboard
    ↓
Settings
```

Only the route names and screens change. The navigation concepts remain exactly the same.