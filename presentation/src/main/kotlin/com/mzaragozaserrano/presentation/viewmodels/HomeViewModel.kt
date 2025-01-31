package com.mzaragozaserrano.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.Result
import com.mzaragozaserrano.domain.usecases.GetAllAdsUseCaseImpl
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.utils.transform
import com.mzaragozaserrano.presentation.vo.AdVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val getAllAds: GetAllAdsUseCaseImpl) : BaseViewModel<List<AdVO>>() {

    fun onCreate() {
        viewModelScope.launch {
            onExecuteGetAllAds()
        }
    }

    private suspend fun onExecuteGetAllAds() = withContext(context = Dispatchers.IO) {
        getAllAds
            .invoke()
            .collect(collector = ::handleGetAllAdsResponse)
    }

    private fun handleGetAllAdsResponse(result: Result<List<AdBO>>) {
        when (result) {
            is Result.Loading -> {

            }
            is Result.Response.Error<*> -> {

            }
            is Result.Response.Success -> {
                setSuccess(data = result.data.transform())
            }
        }
    }

}