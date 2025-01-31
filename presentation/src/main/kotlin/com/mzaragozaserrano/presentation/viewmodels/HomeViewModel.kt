package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.usecases.GetAllAdsUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzs.core.domain.bo.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val getAllAds: GetAllAdsUseCaseImpl) : BaseViewModel<List<AdVO>>() {

    override fun createInitialState(): UiState<List<AdVO>> = UiState()

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

    fun onFavoriteClicked(id: String, isFavorite: Boolean) {
        with(getViewModelState()) {
            success?.firstOrNull { id == it.id }?.isFavorite = isFavorite.not()
            onUpdateUiState { copy(success = success) }
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
                onUpdateUiState {
                    copy(
                        error = null,
                        loading = false,
                        success = result.data.transform()
                    )
                }
            }
        }
    }

}