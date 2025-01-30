package com.mzaragozaserrano.data.dto

sealed class ErrorDTO {
    data object Connectivity: ErrorDTO()
    data object DataNotFound : ErrorDTO()
    data object DeserializingJSON : ErrorDTO()
    data class Generic(val id: Int) : ErrorDTO()
    data object LoadingData : ErrorDTO()
    data object LoadingURL : ErrorDTO()
}