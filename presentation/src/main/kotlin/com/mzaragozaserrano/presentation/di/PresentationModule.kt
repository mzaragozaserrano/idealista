package com.mzaragozaserrano.presentation.di

import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels
    viewModelOf(::HomeViewModel)
}