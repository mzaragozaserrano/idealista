package com.mzaragozaserrano.data.utils

sealed class UrlConstants(val url: String) {

    data object GetAllAds :
        UrlConstants(url = "https://github.com/idealista/android-challenge/blob/master/list.json")

    data object GetAdDetail :
        UrlConstants(url = "https://idealista.github.io/android-challenge/detail.json")

}