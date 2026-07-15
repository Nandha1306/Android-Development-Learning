package com.example.coroutinepractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoadingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow("Press the button")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = "Loading..."
            fetchData()
            _uiState.value = "Data Loaded"
        }
    }

    private suspend fun fetchData() {
        delay(3000)
    }
}