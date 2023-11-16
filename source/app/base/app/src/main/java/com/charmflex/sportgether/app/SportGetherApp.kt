package com.charmflex.sportgether.app

import android.app.Application
import com.charmflex.sportgether.sdk.core.di.MainComponent
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.mock.FakeWebServer

class SportGetherApp : Application(), MainProvider {
    private var appComponent: MainComponent? = null
    private val fakeWebServer: FakeWebServer by lazy { FakeWebServer() }

    override fun onCreate() {
        super.onCreate()
        MainProvider.instance = this

        appComponent = MainComponent.injectCreate(applicationContext)
        startMockWebServer()
    }

    override fun getMainInjector(): MainInjector {
        return appComponent ?: throw IllegalStateException("AppComponent is not initialized yet!")
    }

    private fun startMockWebServer() {
        fakeWebServer.start()
    }
}