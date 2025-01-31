package com.mzaragozaserrano.data.datasources.remote

import com.mzaragozaserrano.data.dto.AdDTO
import com.mzaragozaserrano.data.dto.AdDetailDTO
import com.mzaragozaserrano.data.dto.ResultDTO

interface AdsDataSource {
    suspend fun getAllAds(): ResultDTO<List<AdDTO>>
    suspend fun getAdDetail(): ResultDTO<AdDetailDTO>
}