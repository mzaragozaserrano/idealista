package com.mzaragozaserrano.data.dto

sealed class ResultDTO<out R> {

    data class Response<out R>(val data: R) : ResultDTO<R>()

    data class Error<out E>(val code: E) : ResultDTO<Nothing>()

}