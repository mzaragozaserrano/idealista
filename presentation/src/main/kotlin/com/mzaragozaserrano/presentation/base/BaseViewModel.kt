package com.mzaragozaserrano.presentation.base

import androidx.lifecycle.ViewModel
import com.mzaragozaserrano.presentation.vo.ErrorVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<T> : ViewModel() {

    protected abstract fun createInitialState(): UiState<T>

    private val _uiState by lazy { MutableStateFlow(value = createInitialState()) }
    val uiState = _uiState.asStateFlow()

    protected fun onUpdateUiState(update: UiState<T>.() -> UiState<T>) {
        _uiState.update { it.update() }
    }

    fun getViewModelState(): UiState<T> = _uiState.value

    data class UiState<T>(
        val error: ErrorVO? = null,
        val loading: Boolean = false,
        val success: T? = null,
    )

}