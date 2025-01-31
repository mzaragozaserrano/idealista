package com.mzaragozaserrano.data.utils

sealed class UrlConstants(val url: String) {

    data object GetAllAds :
        UrlConstants(url = "https://idealista.github.io/android-challenge/list.json")

    data object GetDetailedAd :
        UrlConstants(url = "https://idealista.github.io/android-challenge/detail.json")

}