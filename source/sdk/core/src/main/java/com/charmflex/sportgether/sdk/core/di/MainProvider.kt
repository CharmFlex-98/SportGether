package com.charmflex.sportgether.sdk.core.di

import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider

interface MainProvider {

    fun getMainInjector(): MainInjector

    companion object {
        lateinit var instance: MainProvider
    }
}

// For main dependencies
interface MainInjector {
    fun keyStorageProvider(): KeyStorageProvider
}