package com.charmflex.sportgether.sdk.events.internal.event.domain.models

data class EventPageInfoDomainModel(
    val eventInfoDomainModel: List<EventInfoDomainModel>,
    val nextCursorId: String
)
data class EventInfoDomainModel(
    val eventId: Int,
    val eventName: String,
    val startTime: String,
    val endTime: String,
    val place: String,
    val eventType: EventType,
    val host: EventParticipantInfoDomainModel,
    val isHost: Boolean,
    val joiners: List<EventParticipantInfoDomainModel>,
    val maxParticipantCount: Int,
    val description: String,
    val isJoined: Boolean
)

data class EventParticipantInfoDomainModel(
    val userId: Int,
    val username: String,
    val profileIconName: String
)

enum class EventType {
    UNKNOWN, BADMINTON, SOCCER, BASKETBALL
}