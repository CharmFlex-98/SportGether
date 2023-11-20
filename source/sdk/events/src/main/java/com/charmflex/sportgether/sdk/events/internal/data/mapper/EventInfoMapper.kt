package com.charmflex.sportgether.sdk.events.internal.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.data.models.GetEventsResponse
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventParticipantInfo
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventType
import java.time.LocalDateTime
import javax.inject.Inject

internal class EventInfoMapper @Inject constructor()  : Mapper<GetEventsResponse, List<EventInfo>> {
    override fun map(from: GetEventsResponse): List<EventInfo> {
        return from.events.map {
            EventInfo(
                eventName = it.eventTitle,
                startTime = LocalDateTime.parse(it.startTime),
                endTime = LocalDateTime.parse(it.endTime),
                place = it.destination,
                eventType = getEventType(it.eventType),
                host = EventParticipantInfo(
                    it.host.userId,
                    it.host.username,
                    it.host.profileIconUrl
                ),
                joiners = it.joiners.map { participant ->
                    EventParticipantInfo(
                        participant.userId,
                        participant.username,
                        participant.profileIconUrl
                    )
                }
            )
        }
    }

    private fun getEventType(eventType: String): EventType {
        EventType.values().forEach {
            if (it.toString() == eventType) return it
        }
        return EventType.UNKNOWN
    }
}