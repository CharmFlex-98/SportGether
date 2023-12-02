package com.charmflex.sportgether.sdk.events.internal.event.data.api

import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface EventApi {
    @Headers("Content-Type: application/json")
    @GET("v1/event/all")
    suspend fun fetchAllEvents(): GetEventsResponse

    @Headers("Content-Type: application/json")
    @POST("v1/event/create")
    suspend fun createEvent(@Body event: CreateEventInput)
}