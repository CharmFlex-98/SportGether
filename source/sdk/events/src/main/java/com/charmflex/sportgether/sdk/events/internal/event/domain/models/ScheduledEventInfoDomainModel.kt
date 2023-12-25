package com.charmflex.sportgether.sdk.events.internal.event.domain.models

data class ScheduledEventInfoDomainModel(
    val eventId: Int,
    val eventName: String,
    val startTime: String,
    val dayRemaining: Int,
    val destination: String,
    val eventType: EventType
)