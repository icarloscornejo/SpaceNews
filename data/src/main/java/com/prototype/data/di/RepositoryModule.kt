package com.prototype.data.di

import com.prototype.data.impl.ArticleRepositoryImpl
import com.prototype.domain.repository.ArticlesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ArticlesRepository> { ArticleRepositoryImpl(get(), get()) }

}