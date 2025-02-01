package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseComposeViewModel<MainViewModel.MainVO>() {

    override fun createInitialState(): UiState<MainVO> = UiState()

    fun onGoToDetail() {
        onNavigate(NavigationType.Detail)
    }

    data class MainVO(val navigation: NavigationType? = null)


    sealed class NavigationType : NavigationEvent {
        data object Detail : NavigationType()
    }

}