package com.mzaragozaserrano.domain.repositories.remote

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.bo.Result
import kotlinx.coroutines.flow.Flow

interface AdsRepository {
    suspend fun getAllAds(): Flow<Result<List<AdBO>>>
    suspend fun getAdDetail(): Flow<Result<AdBO>>
}