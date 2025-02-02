package com.mzaragozaserrano.domain.usecases

import android.content.Intent
import android.net.Uri
import com.mzs.core.domain.usecases.SyncUseCase

class OpenGoogleMapsUseCaseImpl : SyncUseCase<OpenGoogleMapsUseCaseImpl.Params, Intent>() {

    data class Params(val latitude: Double, val longitude: Double)

    override fun invoke(params: Params): Intent {
        val uri =
            Uri.parse("geo:${params.latitude},${params.longitude}?q=${params.latitude},${params.longitude}")
        return Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
    }

}