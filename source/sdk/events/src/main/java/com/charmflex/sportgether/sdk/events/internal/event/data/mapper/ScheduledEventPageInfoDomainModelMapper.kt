package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetUserEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventPageInfoDomainModel

internal class ScheduledEventPageInfoDomainModelMapper : Mapper<GetUserEventsResponse, ScheduledEventPageInfoDomainModel>{
    override fun map(from: GetUserEventsResponse): ScheduledEventPageInfoDomainModel {
        return ScheduledEventPageInfoDomainModel(
            eventInfoDomainModel = from.userEvents.map {
                EventInfoDomainModel(
                    eventId = it.eventId,
                    eventName = it.eventName,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    place = it.destination,
                    eventType = getEventType(it.eventType),
                    host = EventParticipantInfoDomainModel(
                        username = it.host.username,
                        userId = it.host.userId,
                        profileIconName = it.host.profileIconName
                    ),
                    isHost = it.isHost,
                    joiners = it.joiners.map { pInfo ->
                                             EventParticipantInfoDomainModel(
                                                 userId = pInfo.userId,
                                                 username = pInfo.username,
                                                 profileIconName = pInfo.profileIconName
                                             )
                    },
                    maxParticipantCount = it.maxParticipantCount,
                    description = it.description
                )
            }
        )
    }

    private fun getEventType(eventType: String): EventType {
        EventType.values().forEach {
            if (it.toString() == eventType) return it
        }
        return EventType.UNKNOWN
    }
}