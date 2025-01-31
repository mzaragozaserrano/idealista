package com.mzaragozaserrano.domain.bo

sealed class Result<out T> {

    data object Loading : Result<Nothing>()

    sealed class Response<out T> : Result<T>() {
        data class Success<out T>(val data: T) : Response<T>()
        data class Error<out E>(val code: E) : Response<Nothing>()
    }

}