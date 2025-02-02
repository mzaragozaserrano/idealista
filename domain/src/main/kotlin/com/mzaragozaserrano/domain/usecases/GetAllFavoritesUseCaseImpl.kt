package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.domain.usecases.SyncUseCaseNoParams

class GetAllFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    SyncUseCaseNoParams<List<AdBO>>() {

    override fun invoke(): List<AdBO> = favoritesRepository.getAllFavorites()

}