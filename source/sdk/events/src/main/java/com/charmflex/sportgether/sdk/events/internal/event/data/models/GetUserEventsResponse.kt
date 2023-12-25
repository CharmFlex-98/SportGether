package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName

internal data class GetUserEventsResponse(
    @SerializedName("userEvents")
    val userEvents: List<EventResponse>
)