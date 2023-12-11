package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.CreateEditFieldInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventForModifyUseCase @Inject constructor(
    private val eventService: EventService,
    private val mapper: CreateEditFieldInfoMapper
) {
    suspend operator fun invoke(eventId: Int): Result<List<CreateEditFieldInfo>> {
        return eventService.fetchEvents().first().map {
            val res = it.eventInfo.first { eventInfo -> eventInfo.eventId == eventId }
            mapper.map(res)
        }
    }
}