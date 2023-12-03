package com.charmflex.sportgether.app.home.domain.mappers

import com.charmflex.sportgether.app.home.ui.event.EventBoardViewState
import com.charmflex.sportgether.sdk.core.utils.DATE_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.TIME_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import javax.inject.Inject

class EventBoardDetailsMapper @Inject constructor() : Mapper<EventInfo, EventBoardViewState.EventDetail> {
    override fun map(from: EventInfo): EventBoardViewState.EventDetail {
        return EventBoardViewState.EventDetail(
            eventId = from.eventId,
            eventName = from.eventName,
            eventType = from.eventType.toString(),
            eventDate = from.startTime.fromISOToStringWithPattern(DATE_ONLY_DEFAULT_PATTERN),
            eventDestination = from.place,
            eventHost = from.host.username,
            eventStartTime = from.startTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN),
            eventEndTime = from.endTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN)
        )
    }
}