package com.mzaragozaserrano.presentation.viewmodels

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.usecases.AddFavoriteAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetDetailedAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.RemoveFavoriteAdUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseComposeViewModel
import com.mzaragozaserrano.presentation.fragments.DetailFragment
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.DetailedAdVO
import com.mzs.core.domain.bo.Result
import kotlinx.coroutines.launch

class DetailViewModel(
    private val addFavoriteAd: AddFavoriteAdUseCaseImpl,
    private val getDetailedAd: GetDetailedAdUseCaseImpl,
    private val removeFavoriteAd: RemoveFavoriteAdUseCaseImpl,
) : BaseComposeViewModel<DetailViewModel.DetailVO>() {

    override fun createInitialState(): UiState<DetailVO> = UiState()

    fun onCreate(ad: AdVO?) {
        if (ad != null) {
            onUpdateUiState { copy(success = DetailVO(ad = ad)) }
        }
        onExecuteGetDetailedAd()
    }

    fun onCheckChanged(): Bundle {
        with(getViewModelState()) {
            return bundleOf(DetailFragment.AD_CHANGED to success?.ad)
        }
    }

    fun onExecuteGetDetailedAd() {
        viewModelScope.launch {
            getDetailedAd
                .invoke()
                .collect(::handleGetDetailedAdResponse)
        }
    }

    private fun handleGetDetailedAdResponse(result: Result<DetailedAdBO>) {
        when (result) {
            is Result.Loading -> {
                onUpdateUiState { copy(error = null) }
            }

            is Result.Response.Error<*> -> {
                onUpdateUiState {
                    copy(error = (result.code as ErrorBO).transform())
                }
            }

            is Result.Response.Success -> {
                onUpdateUiState {
                    copy(
                        error = null,
                        success = success?.copy(detailedAd = result.data.transform())
                    )
                }
            }
        }
    }

    fun onSetFavorite(isFavorite: Boolean) {
        with(getViewModelState()) {
            success?.ad?.let { ad ->
                if (isFavorite) {
                    removeFavoriteAd.invoke(params = RemoveFavoriteAdUseCaseImpl.Params(ad = ad.transform()))
                } else {
                    addFavoriteAd.invoke(params = AddFavoriteAdUseCaseImpl.Params(ad = ad.transform()))
                }
                onUpdateUiState {
                    copy(success = success?.copy(ad = ad.copy(isFavorite = isFavorite.not())))
                }
            }
        }
    }

    data class DetailVO(val ad: AdVO, val detailedAd: DetailedAdVO = DetailedAdVO())

}