package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import javax.inject.Inject

internal class EventPageInfoDomainModelMapper @Inject constructor()  : Mapper<GetEventsResponse, EventPageInfoDomainModel> {
    override fun map(from: GetEventsResponse): EventPageInfoDomainModel {
        val events = from.events.map {
            EventInfoDomainModel(
                eventId = it.eventId,
                eventName = it.eventName,
                startTime = it.startTime,
                endTime = it.endTime,
                place = it.destination,
                eventType = getEventType(it.eventType),
                host = EventParticipantInfoDomainModel(
                    it.host.userId,
                    it.host.username,
                    profileIconName = it.host.profileIconName
                ),
                isHost = it.isHost,
                joiners = it.joiners.map { participant ->
                    EventParticipantInfoDomainModel(
                        participant.userId,
                        participant.username,
                        participant.profileIconName
                    )
                },
                maxParticipantCount = it.maxParticipantCount,
                description = it.description
            )
        }
        return EventPageInfoDomainModel(
            eventInfoDomainModel = events,
            nextCursorId = from.nextCursorId
        )
    }

    private fun getEventType(eventType: String): EventType {
        EventType.values().forEach {
            if (it.toString() == eventType) return it
        }
        return EventType.UNKNOWN
    }
}