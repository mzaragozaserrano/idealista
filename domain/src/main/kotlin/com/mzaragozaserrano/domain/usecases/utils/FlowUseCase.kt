package com.mzaragozaserrano.domain.usecases.utils

import com.mzaragozaserrano.domain.bo.Result
import com.mzaragozaserrano.domain.repositories.remote.NetworkRepository
import com.mzaragozaserrano.domain.utils.toFlowResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

@Suppress("UNCHECKED_CAST")
abstract class FlowUseCaseNoParams<out Response, out Error>(
    private val networkError: Error,
    private val networkRepository: NetworkRepository,
) where Response : Any {

    abstract suspend fun run(): Flow<Response>

    suspend operator fun invoke(): Flow<Response> =
        if (networkRepository.isConnected()) {
            run().flowOn(Dispatchers.IO)
        } else {
            (Result.Response.Error(networkError) as Response).toFlowResult()
        }

}