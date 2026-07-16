package com.example.themepreference

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.themepreference.data.ThemePreferences
import com.example.themepreference.ui.ThemeScreen
import com.example.themepreference.ui.ThemeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = ThemePreferences(this)
        val viewModel = ThemeViewModel(preferences)

        setContent {
            ThemeScreen(viewModel)
        }
    }
}