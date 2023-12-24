package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventDetailsUseCase @Inject constructor(
    private val eventService: EventService,
) {

    suspend operator fun invoke(eventId: Int): Result<EventInfoDomainModel> {
        return eventService.fetchEvents().first().map { eventPageInfo ->
            eventPageInfo.eventInfoDomainModel.first { eventInfo -> eventInfo.eventId == eventId }
        }
    }
}