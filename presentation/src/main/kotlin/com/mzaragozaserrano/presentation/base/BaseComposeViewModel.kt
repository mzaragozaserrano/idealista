package com.mzaragozaserrano.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.presentation.vo.ErrorVO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseComposeViewModel<T> : ViewModel() {

    protected abstract fun createInitialState(): UiState<T>

    private val _uiState by lazy { MutableStateFlow(value = createInitialState()) }
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    protected fun onUpdateUiState(update: UiState<T>.() -> UiState<T>) {
        _uiState.update { it.update() }
    }

    protected fun onNavigate(destination: NavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.emit(value = destination)
        }
    }

    fun getViewModelState(): UiState<T> = _uiState.value

    data class UiState<T>(
        val error: ErrorVO? = null,
        val loading: Boolean = false,
        val success: T? = null,
    )

    interface NavigationEvent

}