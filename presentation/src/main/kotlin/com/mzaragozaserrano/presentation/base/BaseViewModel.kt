package com.mzaragozaserrano.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {

    private val _uiState = MutableLiveData<UiState<T>>()
    val uiState: LiveData<UiState<T>> get() = _uiState

    protected fun setLoading() {
        _uiState.postValue(UiState.Loading)
    }

    protected fun setError() {
        _uiState.postValue(UiState.Error)
    }

    protected fun setSuccess(data: T) {
        _uiState.postValue(UiState.Success(data))
    }

    sealed class UiState<out T> {
        data object Error : UiState<Nothing>()
        data object Loading : UiState<Nothing>()
        data class Success<T>(val data: T) : UiState<T>()
    }

}