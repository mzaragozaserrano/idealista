package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.usecases.AddFavoriteAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetAllAdsUseCaseImpl
import com.mzaragozaserrano.domain.usecases.RemoveFavoriteAdUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzaragozaserrano.presentation.vo.Type
import com.mzs.core.domain.bo.Result
import kotlinx.coroutines.launch

class HomeViewModel(
    private val addFavoriteAd: AddFavoriteAdUseCaseImpl,
    private val getAllAds: GetAllAdsUseCaseImpl,
    private val removeFavoriteAd: RemoveFavoriteAdUseCaseImpl,
) : BaseViewModel<HomeViewModel.HomeVO>() {

    override fun createInitialState(): UiState<HomeVO> = UiState()

    fun onCreate() {
        onExecuteGetAllAds()
    }

    fun onExecuteGetAllAds() {
        viewModelScope.launch {
            getAllAds
                .invoke()
                .collect(collector = ::handleGetAllAdsResponse)
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
                    currentAds = filteredList.orEmpty(),
                    optionSelected = optionSelected
                )
            )
        }
    }

    fun onFavoriteClicked(id: String, isFavorite: Boolean) {
        with(getViewModelState()) {
            success?.currentAds?.firstOrNull { it.id == id }?.let { ad ->
                if (isFavorite) {
                    removeFavoriteAd.invoke(params = RemoveFavoriteAdUseCaseImpl.Params(ad = ad.transform()))
                } else {
                    addFavoriteAd.invoke(params = AddFavoriteAdUseCaseImpl.Params(ad = ad.transform()))
                }
                onUpdateUiState {
                    copy(
                        success = success?.copy(
                            allAds = success.allAds.map {
                                if (it.id == id) {
                                    it.copy(isFavorite = isFavorite.not())
                                } else {
                                    it
                                }
                            },
                            currentAds = success.currentAds.map {
                                if (it.id == id) {
                                    it.copy(isFavorite = isFavorite.not())
                                } else {
                                    it
                                }
                            }
                        )
                    )
                }
            }
        }
    }

    private fun handleGetAllAdsResponse(result: Result<List<AdBO>>) {
        when (result) {
            is Result.Loading -> {
                onUpdateUiState { copy(error = null, loading = true) }
            }

            is Result.Response.Error<*> -> {
                onUpdateUiState {
                    copy(
                        error = (result.code as ErrorBO).transform(),
                        loading = false
                    )
                }
            }

            is Result.Response.Success -> {
                val ads = result.data.transform()
                onUpdateUiState {
                    copy(
                        error = null,
                        loading = false,
                        success = HomeVO(allAds = ads, currentAds = ads)
                    )
                }
            }
        }
    }

    data class HomeVO(
        val currentAds: List<AdVO>,
        val allAds: List<AdVO>,
        val optionSelected: Filter? = null,
    )

}