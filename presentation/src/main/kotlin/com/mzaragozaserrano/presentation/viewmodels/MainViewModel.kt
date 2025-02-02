package com.mzaragozaserrano.presentation.viewmodels

import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.composables.navigation.Favorites
import com.mzaragozaserrano.presentation.composables.navigation.Home
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.BottomItem
import com.mzaragozaserrano.presentation.vo.Filter

class MainViewModel : BaseComposeViewModel<MainViewModel.MainVO>() {

    override fun createInitialState(): UiState<MainVO> = UiState(success = MainVO())

    fun onSetForceToRefresh(adChanged: AdVO?) {
        onUpdateUiState { copy(success = success?.copy(adChanged = adChanged)) }
    }

    fun onGoToDetail(ad: AdVO) {
        onNavigate(NavigationType.Detail(ad = ad))
    }

    fun onSetDestination(item: BottomItem) {
        val destination: Any = when (item) {
            is BottomItem.Favorite -> {
                Favorites
            }

            is BottomItem.Home -> {
                Home
            }
        }
        onUpdateUiState {
            copy(
                success = success?.copy(
                    bottomItem = item,
                    destination = destination
                )
            )
        }
    }

    fun onSetFilter(option: Filter) {
        onUpdateUiState { copy(success = success?.copy(optionSelected = option)) }
    }

    data class MainVO(
        val adChanged: AdVO? = null,
        val bottomItem: BottomItem = BottomItem.Home,
        val destination: Any = Home,
        val navigation: NavigationType? = null,
        val optionSelected: Filter? = null,
    )

    sealed class NavigationType : NavigationEvent {
        data class Detail(val ad: AdVO) : NavigationType()
    }

}