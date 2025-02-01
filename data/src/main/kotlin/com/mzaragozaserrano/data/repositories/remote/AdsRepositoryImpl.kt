package com.mzaragozaserrano.data.repositories.remote

import com.mzaragozaserrano.data.datasources.local.database.FavoritesDataSource
import com.mzaragozaserrano.data.datasources.remote.AdsDataSource
import com.mzaragozaserrano.data.dto.ErrorDTO
import com.mzaragozaserrano.data.dto.ResultDTO
import com.mzaragozaserrano.data.utils.transform
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.DetailedAdBO
import com.mzs.core.domain.bo.Result
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import com.mzs.core.data.datasources.local.ResourcesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AdsRepositoryImpl(
    private val adsDataSource: AdsDataSource,
    private val resourcesDataSource: ResourcesDataSource,
) : AdsRepository {

    override suspend fun getAllAds(): Flow<Result<List<AdBO>>> = flow {
        emit(Result.Loading)
        emit(
            when (val result = adsDataSource.getAllAds()) {
                is ResultDTO.Response -> {
                    Result.Response.Success(data = result.data.transform(resourcesDataSource = resourcesDataSource))
                }

                is ResultDTO.Error<*> -> {
                    Result.Response.Error(code = (result.code as ErrorDTO).transform())
                }
            }
        )
    }

    override suspend fun getDetailedAd(): Flow<Result<DetailedAdBO>> = flow {
        emit(Result.Loading)
        emit(
            when (val result = adsDataSource.getDetailedAd()) {
                is ResultDTO.Response -> {
                    Result.Response.Success(data = result.data.transform())
                }

                is ResultDTO.Error<*> -> {
                    Result.Response.Error(code = (result.code as ErrorDTO).transform())
                }
            }
        )
    }

}