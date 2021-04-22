package com.prototype.spacenews.di

import com.prototype.data.util.ResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {

    // Resource Manager
    single { ResourceManager(androidContext()) }
}