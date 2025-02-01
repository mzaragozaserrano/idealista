package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.usecases.GetAllFavoritesUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.FavoriteAdVO
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzaragozaserrano.presentation.vo.Type
import com.mzs.core.domain.utils.generic.DateUtils
import com.mzs.core.domain.utils.generic.ddMMyyyy
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val dateUtils: DateUtils,
    private val getAllFavorites: GetAllFavoritesUseCaseImpl,
) :
    BaseViewModel<FavoritesViewModel.FavoritesVO>() {

    override fun createInitialState(): UiState<FavoritesVO> = UiState()

    fun onCreate(optionSelected: Filter?) {
        onExecuteGetAllFavorites(optionSelected = optionSelected)
    }

    fun onExecuteGetAllFavorites(optionSelected: Filter?) {
        viewModelScope.launch {
            onUpdateUiState { copy(loading = true) }
            val ads = getAllFavorites.invoke().map { it.transform() }
            val filteredList = if (optionSelected is Type) {
                ads.filter { ad ->
                    ad.type == (optionSelected as? Type)?.type
                }
            } else {
                ads
            }
            onUpdateUiState {
                copy(
                    loading = false,
                    success = FavoritesVO(
                        allAds = ads,
                        currentAds = filteredList.transform(dateUtils = dateUtils),
                        optionSelected = optionSelected
                    )
                )
            }
        }
    }

    fun onRefreshList(optionSelected: Filter) {
        onUpdateUiState { copy(loading = true) }
        onUpdateUiState {
            val filteredList = if (optionSelected is Type) {
                success?.allAds?.filter { ad ->
                    ad.type == (optionSelected as? Type)?.type
                }
            } else {
                success?.allAds
            }
            copy(
                loading = false,
                success = success?.copy(
                    currentAds = filteredList?.transform(dateUtils = dateUtils).orEmpty(),
                    optionSelected = optionSelected
                )
            )
        }
    }

    data class FavoritesVO(
        val allAds: List<FavoriteAdVO>,
        val currentAds: Map<String, List<FavoriteAdVO>>,
        val optionSelected: Filter? = null,
    )

}