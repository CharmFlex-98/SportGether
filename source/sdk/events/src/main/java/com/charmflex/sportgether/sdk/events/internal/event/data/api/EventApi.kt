package com.charmflex.sportgether.sdk.events.internal.event.data.api

import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetUserEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.data.models.JoinEventInput
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface EventApi {
    @Headers("Content-Type: application/json")
    @POST("v1/event/all")
    suspend fun fetchAllEvents(@Body input: GetEventsInput): GetEventsResponse

    @Headers("Content-Type: application/json")
    @POST("v1/user/event")
    suspend fun fetchUserEvents(): GetUserEventsResponse

    @Headers("Content-Type: application/json")
    @POST("v1/event/create")
    suspend fun createEvent(@Body event: CreateEventInput)

    @Headers("Content-Type: application/json")
    @POST("v1/event/join")
    suspend fun joinEvent(@Body input: JoinEventInput)
}