package com.mzaragozaserrano.domain.usecases

import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.domain.usecases.SyncUseCase

class AddFavoriteAdUseCaseImpl(private val favoritesRepository: FavoritesRepository) :
    SyncUseCase<AddFavoriteAdUseCaseImpl.Params, Boolean>() {

    data class Params(val ad: AdBO)

    override fun invoke(params: Params): Boolean =
        favoritesRepository.addFavorite(ad = params.ad)

}