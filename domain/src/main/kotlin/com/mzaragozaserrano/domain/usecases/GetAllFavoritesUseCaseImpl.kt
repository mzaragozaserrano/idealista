package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.FavoriteAdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.domain.usecases.SyncUseCaseNoParams

class GetAllFavoritesUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    SyncUseCaseNoParams<List<FavoriteAdBO>>() {

    override fun invoke(): List<FavoriteAdBO> = favoritesRepository.getAllFavorites()

}