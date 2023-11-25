package com.charmflex.sportgether.sdk.events.internal.event.domain.models

import java.time.LocalDateTime
data class EventInfo(
    val eventId: Int,
    val eventName: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val place: String,
    val eventType: EventType,
    val host: EventParticipantInfo,
    val joiners: List<EventParticipantInfo>,
    val maxParticipantCount: Int,
    val description: String
)

data class EventParticipantInfo(
    val userId: Int,
    val username: String,
    val profileIconUrl: String
)

enum class EventType {
    UNKNOWN, BADMINTON, SOCCER, BASKETBALL
}