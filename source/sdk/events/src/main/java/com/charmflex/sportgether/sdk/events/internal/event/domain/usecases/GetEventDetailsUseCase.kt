package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.EventDetailsMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventDetailsUseCase @Inject constructor(
    private val eventService: EventService,
    private val mapper: EventDetailsMapper
) {
    suspend operator fun invoke(eventId: Int): Result<List<EventDetailField>> {
        return eventService.fetchEvents().first().fold(
            onSuccess = { eventInfoList ->
                val res = eventInfoList.first { eventInfo -> eventInfo.eventId == eventId }
                return@fold Result.success(mapper.map(res))
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}