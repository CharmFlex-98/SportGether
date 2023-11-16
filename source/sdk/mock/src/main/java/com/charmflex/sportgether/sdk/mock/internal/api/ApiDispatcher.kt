package com.charmflex.sportgether.sdk.mock.internal.api

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer

class ApiDispatcher: Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            request.matches("POST", "/v1/user/login") -> successMockResponse()
            else -> MockResponse()
        }
    }

    private fun RecordedRequest.matches(method: String, pattern: String): Boolean {
        val regex = Regex(pattern)
        return regex.matches(this.path.orEmpty()) && this.method.equals(method, ignoreCase = true)
    }

    private fun successMockResponse(): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .addHeader("application/json")
            .setBody(Buffer())
    }

    private fun errorMockResponse(): MockResponse {
        return MockResponse()
            .setResponseCode(400)
            .addHeader("application/json")
            .setBody(Buffer())
    }
}