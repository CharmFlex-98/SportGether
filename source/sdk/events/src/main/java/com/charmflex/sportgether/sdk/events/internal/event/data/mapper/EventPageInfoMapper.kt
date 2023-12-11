package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import javax.inject.Inject

internal class EventPageInfoMapper @Inject constructor()  : Mapper<GetEventsResponse, EventPageInfo> {
    override fun map(from: GetEventsResponse): EventPageInfo {
        val events = from.events.map {
            EventInfo(
                eventId = it.eventId,
                eventName = it.eventName,
                startTime = it.startTime,
                endTime = it.endTime,
                place = it.destination,
                eventType = getEventType(it.eventType),
                host = EventParticipantInfo(
                    it.host.userId,
                    it.host.username,
                ),
                joiners = it.joiners.map { participant ->
                    EventParticipantInfo(
                        participant.userId,
                        participant.username,
                    )
                },
                maxParticipantCount = it.maxParticipantCount,
                description = it.description
            )
        }
        return EventPageInfo(
            eventInfo = events,
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