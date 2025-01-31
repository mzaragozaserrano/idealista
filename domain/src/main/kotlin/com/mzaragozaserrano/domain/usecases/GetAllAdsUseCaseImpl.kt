package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.Result
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import com.mzaragozaserrano.domain.repositories.remote.NetworkRepository
import com.mzaragozaserrano.domain.usecases.utils.FlowUseCaseNoParams
import kotlinx.coroutines.flow.Flow

class GetAllAdsUseCaseImpl(
    private val adsRepository: AdsRepository,
    networkRepository: NetworkRepository,
) : FlowUseCaseNoParams<Result<List<AdBO>>, ErrorBO>(
    networkRepository = networkRepository,
    networkError = ErrorBO.Connectivity
) {

    override suspend fun run(): Flow<Result<List<AdBO>>> = adsRepository.getAllAds()

}