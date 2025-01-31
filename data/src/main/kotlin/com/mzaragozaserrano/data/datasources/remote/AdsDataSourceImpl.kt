package com.mzaragozaserrano.data.datasources.remote

import com.mzaragozaserrano.data.dto.AdDTO
import com.mzaragozaserrano.data.dto.AdDetailDTO
import com.mzaragozaserrano.data.dto.ErrorDTO
import com.mzaragozaserrano.data.dto.ResultDTO
import com.mzaragozaserrano.data.utils.UrlConstants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

class AdsDataSourceImpl : AdsDataSource {

    override suspend fun getAllAds(): ResultDTO<List<AdDTO>> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, AdDTO::class.java)
        val adapter: JsonAdapter<List<AdDTO>> = moshi.adapter(listType)
        return doRequest(
            adapter = adapter,
            url = UrlConstants.GetAllAds.url
        )
    }

    override suspend fun getAdDetail(): ResultDTO<AdDetailDTO> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<AdDetailDTO> = moshi.adapter(AdDetailDTO::class.java)
        return doRequest(
            adapter = adapter,
            url = UrlConstants.GetAdDetail.url
        )
    }

    private fun <Type> doRequest(
        url: String, adapter: JsonAdapter<Type>,
    ): ResultDTO<Type> {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val dto = body?.let { adapter.fromJson(it) }
                    dto?.let { ResultDTO.Response(it) } ?: ResultDTO.Error(ErrorDTO.DataNotFound)
                } else {
                    ResultDTO.Error(ErrorDTO.Generic(response.code))
                }
            }
        } catch (e: JsonDataException) {
            ResultDTO.Error(ErrorDTO.DeserializingJSON)
        } catch (e: Exception) {
            ResultDTO.Error(ErrorDTO.LoadingData)
        }
    }

}