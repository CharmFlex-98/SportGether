package com.charmflex.sportgether.sdk.mock.internal.api

import android.content.Context
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import java.io.InputStreamReader

class ApiDispatcher(
    private val appContext: Context
): Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            request.matches("POST", "/v1/user/login") -> successMockResponse("post_user_login.json")
            request.matches(method = "GET", "/v1/event/all") -> successMockResponse("get_event_all.json")
            else -> MockResponse()
        }
    }

    private fun RecordedRequest.matches(method: String, pattern: String): Boolean {
        val regex = Regex(pattern)
        return regex.matches(this.path.orEmpty()) && this.method.equals(method, ignoreCase = true)
    }

    private fun successMockResponse(fileName: String): MockResponse {
        return MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(readAsset(fileName = fileName))
    }

    private fun errorMockResponse(): MockResponse {
        return MockResponse()
            .setResponseCode(400)
            .addHeader("application/json")
            .setBody(Buffer())
    }

    private fun readAsset(fileName: String): String {
        val inputStream = appContext.assets.open(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}