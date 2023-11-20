package com.charmflex.sportgether.app

import android.app.Application
import android.util.Log
import com.charmflex.sportgether.app.di.AppComponent
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.mock.FakeWebServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SportGetherApp : Application(), MainProvider {
    private var appComponent: AppComponent? = null
    private val fakeWebServer: FakeWebServer by lazy { FakeWebServer(applicationContext) }
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        MainProvider.instance = this

        appComponent = AppComponent.injectCreate(applicationContext)
        coroutineScope.launch {
            try {
                startMockWebServer()
            } catch (e: Exception) {
                Log.d("test", "onCreate: $e")
            }
        }
    }

    override fun getMainInjector(): MainInjector {
        return appComponent ?: throw IllegalStateException("AppComponent is not initialized yet!")
    }

    private fun startMockWebServer() {
        fakeWebServer.start()
    }
}