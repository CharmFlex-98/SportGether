package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName


data class GetEventsResponse(
    @SerializedName("events")
    val events: List<EventResponse>,
    @SerializedName("nextCursorId")
    val nextCursorId: String
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
    @SerializedName("isHost")
    val isHost: Boolean,
    @SerializedName("participants")
    val joiners: List<EventParticipantResponse>,
    @SerializedName("description")
    val description: String,
    @SerializedName("isJoined")
    val isJoined: Boolean
)

data class EventParticipantResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("profileIconName")
    val profileIconName: String
)