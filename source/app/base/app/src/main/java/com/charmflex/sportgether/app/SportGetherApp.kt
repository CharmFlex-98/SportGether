package com.charmflex.sportgether.app

import android.app.Application
import com.charmflex.sportgether.sdk.core.di.MainComponent
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider

class SportGetherApp : Application(), MainProvider {
    private var appComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        MainProvider.instance = this

        appComponent = MainComponent.injectCreate(applicationContext)
    }

    override fun getMainInjector(): MainInjector {
        return appComponent ?: throw IllegalStateException("AppComponent is not initialized yet!")
    }


}