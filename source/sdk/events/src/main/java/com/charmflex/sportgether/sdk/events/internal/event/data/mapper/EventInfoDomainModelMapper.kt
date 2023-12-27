package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.EventResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import javax.inject.Inject

internal class EventInfoDomainModelMapper @Inject constructor() : Mapper<EventResponse, EventInfoDomainModel> {
    override fun map(from: EventResponse): EventInfoDomainModel {
        return EventInfoDomainModel(
            eventId = from.eventId,
            eventName = from.eventName,
            startTime = from.startTime,
            endTime = from.endTime,
            place = from.destination,
            eventType = getEventType(from.eventType),
            host = EventParticipantInfoDomainModel(
                from.host.userId,
                from.host.username,
                profileIconName = from.host.profileIconName
            ),
            isHost = from.isHost,
            joiners = from.joiners.map { participant ->
                EventParticipantInfoDomainModel(
                    participant.userId,
                    participant.username,
                    participant.profileIconName
                )
            },
            maxParticipantCount = from.maxParticipantCount,
            description = from.description,
            isJoined = from.isJoined
        )
    }

    private fun getEventType(eventType: String): EventType {
        EventType.values().forEach {
            if (it.toString() == eventType) return it
        }
        return EventType.UNKNOWN
    }
}