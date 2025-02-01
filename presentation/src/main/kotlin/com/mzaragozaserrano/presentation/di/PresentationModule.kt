package com.mzaragozaserrano.presentation.di

import com.mzaragozaserrano.presentation.viewmodels.FavoritesViewModel
import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import com.mzaragozaserrano.presentation.viewmodels.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::MainViewModel)
}