package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.bo.Result
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import com.mzs.core.domain.repositories.NetworkRepository
import com.mzs.core.domain.usecases.FlowUseCaseNoParams
import kotlinx.coroutines.flow.Flow

class GetDetailedAdUseCaseImpl(
    private val adsRepository: AdsRepository,
    networkRepository: NetworkRepository,
) : FlowUseCaseNoParams<Result<DetailedAdBO>, ErrorBO>(
    networkRepository = networkRepository,
    networkError = ErrorBO.Connectivity
) {

    override suspend fun run(): Flow<Result<DetailedAdBO>> = adsRepository.getdetailedAd()

}