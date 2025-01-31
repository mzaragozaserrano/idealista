package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class ErrorVO(@StringRes open val messageId: Int) {
    data object Connectivity : ErrorVO(messageId = R.string.error_connectivity)
    data object DataNotFound : ErrorVO(messageId = R.string.error_data_not_found)
    data object DeserializingJSON : ErrorVO(messageId = R.string.error_deserializing_json)
//    data object FavoritesNotFound : ErrorVO(messageId = R.string.warning_favorites_not_found)
    data class Generic(val id: Int) : ErrorVO(messageId = id)
    data object LoadingData : ErrorVO(messageId = R.string.error_loading_data)
    data object LoadingURL : ErrorVO(messageId = R.string.error_loading_url)
}