package com.charmflex.sportgether.sdk.mock

import com.charmflex.sportgether.sdk.mock.internal.api.ApiDispatcher
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer

class FakeWebServer {
    private val server: MockWebServer by lazy { MockWebServer() }

    fun start() {
        server.dispatcher = createApiDispatcher()
        server.start(6969)
        server.url("/")
    }

    private fun createApiDispatcher(): Dispatcher {
        return ApiDispatcher()
    }


}