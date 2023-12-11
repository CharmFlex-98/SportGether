package com.charmflex.sportgether.sdk.events.internal.event.data.models

import com.google.gson.annotations.SerializedName

data class GetEventsInput(
    @SerializedName("prevCursor")
    val prevCursor: String? = null,
    @SerializedName("nextCursor")
    val nextCursor: String? = null,
    @SerializedName("pageSize")
    val pageSize: Int = 3
)

fun GetEventsInput.isLoadFirstPage() = prevCursor == null && nextCursor == null