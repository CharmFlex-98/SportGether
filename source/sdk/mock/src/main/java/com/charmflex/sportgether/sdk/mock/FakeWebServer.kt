package com.charmflex.sportgether.sdk.mock

import android.content.Context
import com.charmflex.sportgether.sdk.mock.internal.api.ApiDispatcher
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer

class FakeWebServer(
    private val appContext: Context
) {
    private val server: MockWebServer by lazy { MockWebServer() }

    fun start() {
        server.start(56789)
        server.dispatcher = createApiDispatcher()
        server.url("/").toString()
    }

    private fun createApiDispatcher(): Dispatcher {
        return ApiDispatcher(appContext)
    }


}