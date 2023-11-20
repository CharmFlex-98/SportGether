package com.charmflex.sportgether.sdk.core.di

// This is the only singleton, that store our app instance.
// For easier access to Application() class and obtain shared info.
interface MainProvider {

    fun getMainInjector(): MainInjector

    companion object {
        lateinit var instance: MainProvider
    }
}