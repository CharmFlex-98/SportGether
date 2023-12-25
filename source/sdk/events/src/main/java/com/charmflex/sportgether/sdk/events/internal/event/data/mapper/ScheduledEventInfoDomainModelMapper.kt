package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.TIME_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.fromISOToLocalDateTime
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetUserEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class ScheduledEventInfoDomainModelMapper @Inject constructor() :
    Mapper<GetUserEventsResponse, List<ScheduledEventInfoDomainModel>> {
    override fun map(from: GetUserEventsResponse): List<ScheduledEventInfoDomainModel> {
        return from.userEvents.map {
            val startTime = it.startTime.fromISOToLocalDateTime()
            val endTime = it.endTime.fromISOToLocalDateTime()
            ScheduledEventInfoDomainModel(
                eventId = it.eventId,
                eventName = it.eventName,
                startTime = it.startTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN),
                dayRemaining = endTime.compareTo(startTime).toDuration(DurationUnit.DAYS)
                    .toInt(DurationUnit.DAYS),
                destination = it.destination,
                eventType = getEventType(it.eventType)
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