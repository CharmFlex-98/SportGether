package com.charmflex.sportgether.sdk.events.internal.data.models

import com.google.gson.annotations.SerializedName


data class GetEventsResponse(
    @SerializedName("events")
    val events: List<EventResponse>
)

data class EventResponse(
    @SerializedName("eventTitle")
    val eventTitle: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("eventType")
    val eventType: String,
    @SerializedName("host")
    val host: EventParticipantResponse,
    @SerializedName("joiners")
    val joiners: List<EventParticipantResponse>
)

data class EventParticipantResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("profileIconUrl")
    val profileIconUrl: String
)