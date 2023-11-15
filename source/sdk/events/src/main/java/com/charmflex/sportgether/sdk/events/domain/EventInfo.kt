package com.charmflex.sportgether.sdk.events.domain

import java.time.LocalDate
import java.time.LocalDateTime

internal data class EventInfo(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val place: String,
    val eventType: EventType,
    val host: String,
    val joiner: String
)

internal enum class EventType {
    BADMINTON, SOCCER, BASKETBALL
}