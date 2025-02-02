package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.domain.usecases.SyncUseCase

class RemoveFavoriteAdUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    SyncUseCase<RemoveFavoriteAdUseCaseImpl.Params, Boolean>() {

    data class Params(val ad: AdBO)

    override fun invoke(params: Params): Boolean =
        favoritesRepository.removeFavorite(ad = params.ad)

}