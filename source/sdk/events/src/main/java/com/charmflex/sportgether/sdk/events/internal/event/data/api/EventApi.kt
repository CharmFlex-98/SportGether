package com.charmflex.sportgether.sdk.events.internal.event.data.api

import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface EventApi {
    @Headers("Content-Type: application/json")
    @GET("/v1/event/all")
    suspend fun fetchAllEvents(): GetEventsResponse
}