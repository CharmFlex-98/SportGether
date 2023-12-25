package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName

internal data class GetUserEventsResponse(
    @SerializedName("userEvents")
    val userEvents: List<UserEventDetailResponse>
)

internal data class UserEventDetailResponse(
    @SerializedName("eventId")
    val eventId: Int,
    @SerializedName("eventName")
    val eventName: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("eventType")
    val eventType: String
)