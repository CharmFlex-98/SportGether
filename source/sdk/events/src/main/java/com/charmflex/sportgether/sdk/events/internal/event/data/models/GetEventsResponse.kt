package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName


data class GetEventsResponse(
    @SerializedName("events")
    val events: List<EventResponse>
)

data class EventResponse(
    @SerializedName("id")
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
    val eventType: String,
    @SerializedName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerializedName("host")
    val host: EventParticipantResponse,
    @SerializedName("participants")
    val joiners: List<EventParticipantResponse>,
    @SerializedName("description")
    val description: String
)

data class EventParticipantResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    val username: String,
)