package com.charmflex.sportgether.sdk.events.domain

import java.time.LocalDate
import java.time.LocalDateTime
data class EventInfo(
    val theme: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val place: String,
    val eventType: EventType,
    val host: String,
    val joiner: String
)

enum class EventType {
    BADMINTON, SOCCER, BASKETBALL
}