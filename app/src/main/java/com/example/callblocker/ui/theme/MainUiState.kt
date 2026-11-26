package com.example.callblocker.ui.theme

data class MainUiState(
    val isEnabled: Boolean,
    val prefixes: List<String>,
    val input: String = "",
    val isInputError: Boolean = false,
)