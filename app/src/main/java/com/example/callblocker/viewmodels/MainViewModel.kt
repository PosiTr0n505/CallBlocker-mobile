package com.example.callblocker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.callblocker.models.PrefixRepository
import com.example.callblocker.ui.theme.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PrefixRepository(application)

    private val _state = MutableStateFlow(
        MainUiState(isEnabled = repository.isEnabled, prefixes = repository.all().sorted())
    )
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    fun onToggle(enabled: Boolean) {
        repository.isEnabled = enabled
        _state.update { it.copy(isEnabled = enabled) }
    }

    fun onInputChange(value: String) {
        _state.update { it.copy(input = value, isInputError = false) }
    }

    fun onAdd() {
        val added = repository.add(_state.value.input)
        _state.update {
            if (added) it.copy(input = "", prefixes = repository.all().sorted())
            else it.copy(isInputError = true)
        }
    }

    fun onRemove(prefix: String) {
        repository.remove(prefix)
        _state.update { it.copy(prefixes = repository.all().sorted()) }
    }
}