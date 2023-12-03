package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.EventDetailFieldInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailFieldInfo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventDetailsUseCase @Inject constructor(
    private val eventService: EventService,
    private val mapper: EventDetailFieldInfoMapper
) {

    suspend operator fun invoke(eventId: Int): Result<List<EventDetailFieldInfo>> {
        return eventService.fetchEvents().first().map { eventInfoList ->
            val res = eventInfoList.first { eventInfo -> eventInfo.eventId == eventId }
            mapper.map(res)
        }
    }
}