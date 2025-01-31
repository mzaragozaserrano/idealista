package com.mzaragozaserrano.domain.bo

sealed class ErrorBO {
    data object Connectivity: ErrorBO()
    data object DataNotFound : ErrorBO()
    data object DeserializingJSON : ErrorBO()
    data class Generic(val id: Int) : ErrorBO()
    data object LoadingData : ErrorBO()
    data object LoadingURL : ErrorBO()
}