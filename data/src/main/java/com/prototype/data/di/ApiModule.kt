package com.prototype.data.di

import com.prototype.data.api.SpaceNewsApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {
        get<Retrofit>().create(SpaceNewsApi::class.java)
    }

}