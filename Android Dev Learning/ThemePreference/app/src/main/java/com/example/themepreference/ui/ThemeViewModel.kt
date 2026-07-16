package com.example.themepreference.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themepreference.data.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val preferences: ThemePreferences
) : ViewModel() {

    private val _theme = MutableStateFlow(false)
    val theme = _theme.asStateFlow()

    init {
        saveTheme()
        loadTheme()
    }

    private fun saveTheme() {
        viewModelScope.launch {
            preferences.saveTheme(true)
        }
    }

    private fun loadTheme() {
        viewModelScope.launch {
            preferences.getTheme().collect {
                _theme.value = it
            }
        }
    }
}