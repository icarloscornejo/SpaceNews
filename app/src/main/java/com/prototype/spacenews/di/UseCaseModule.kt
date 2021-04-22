package com.prototype.spacenews.di

import com.prototype.domain.usecase.FetchArticlesUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { FetchArticlesUseCase(get()) }

}