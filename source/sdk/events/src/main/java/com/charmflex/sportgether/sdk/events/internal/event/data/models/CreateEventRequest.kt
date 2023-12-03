package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName

data class CreateEventInput(
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
    @SerializedName("description")
    val description: String
)