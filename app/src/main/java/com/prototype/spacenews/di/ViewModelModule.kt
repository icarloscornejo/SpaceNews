package com.prototype.spacenews.di

import com.prototype.spacenews.presentation.detail.DetailViewModel
import com.prototype.spacenews.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel() }

}