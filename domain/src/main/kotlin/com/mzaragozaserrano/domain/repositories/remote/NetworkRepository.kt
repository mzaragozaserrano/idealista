package com.mzaragozaserrano.domain.repositories.remote

interface NetworkRepository {
    fun isConnected(): Boolean
}