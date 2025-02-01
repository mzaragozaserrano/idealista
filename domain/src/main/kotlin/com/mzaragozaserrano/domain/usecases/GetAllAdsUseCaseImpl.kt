package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.ErrorBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import com.mzs.core.domain.bo.Result
import com.mzs.core.domain.repositories.NetworkRepository
import com.mzs.core.domain.usecases.FlowUseCaseNoParams
import com.mzs.core.domain.utils.extensions.toFlowResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge

class GetAllAdsUseCaseImpl(
    private val adsRepository: AdsRepository,
    private val favoritesRepository: FavoritesRepository,
    networkRepository: NetworkRepository,
) : FlowUseCaseNoParams<Result<List<AdBO>>, ErrorBO>(
    networkRepository = networkRepository,
    networkError = ErrorBO.Connectivity
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun run(): Flow<Result<List<AdBO>>> =
        adsRepository.getAllAds().flatMapMerge { result -> executeGetFavoriteAds(result = result) }

    private fun executeGetFavoriteAds(result: Result<List<AdBO>>) =
        when (result) {
            is Result.Loading -> {
                Result.Loading.toFlowResult()
            }

            is Result.Response.Error<*> -> {
                Result.Response.Error(code = result.code as ErrorBO).toFlowResult()
            }

            is Result.Response.Success -> {
                val listFavoriteIds = favoritesRepository.getAllFavorites().map { it.id }
                val list = result.data
                list.map { ad ->
                    ad.isFavorite = ad.propertyCode in listFavoriteIds
                }
                Result.Response.Success(data = list).toFlowResult()
            }
        }

}