package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName

internal data class JoinEventInput(
    @SerializedName("eventId")
    val eventId: Int
)