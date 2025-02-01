package com.mzaragozaserrano.presentation.viewmodels

import com.mzaragozaserrano.presentation.base.BaseComposeViewModel

class DetailViewModel : BaseComposeViewModel<DetailViewModel.MainVO>() {

    override fun createInitialState(): UiState<MainVO> = UiState()

    fun onGoToDetail() {
        onNavigate(NavigationType.Detail)
    }

    data class MainVO(val navigation: NavigationType? = null)


    sealed class NavigationType : NavigationEvent {
        data object Detail : NavigationType()
    }

}