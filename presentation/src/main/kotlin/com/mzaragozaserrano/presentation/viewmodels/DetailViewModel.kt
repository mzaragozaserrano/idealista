package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.usecases.GetDetailedAdUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.DetailedAdVO
import com.mzs.core.domain.bo.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val getDetailedAd: GetDetailedAdUseCaseImpl) :
    BaseComposeViewModel<DetailViewModel.DetailVO>() {

    override fun createInitialState(): UiState<DetailVO> = UiState()

    fun onCreate() {
        onExecuteGetDetailedAd()
    }

    fun onExecuteGetDetailedAd() {
        viewModelScope.launch {
            getDetailedAd
                .invoke()
                .collect(::handleGetAllAdsResponse)
        }
    }

    private fun handleGetAllAdsResponse(result: Result<DetailedAdBO>) {
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
                val ad = result.data.transform()
                onUpdateUiState {
                    copy(
                        error = null,
                        loading = false,
                        success = DetailVO(ad = ad)
                    )
                }
            }
        }
    }

    data class DetailVO(val ad: DetailedAdVO)

}