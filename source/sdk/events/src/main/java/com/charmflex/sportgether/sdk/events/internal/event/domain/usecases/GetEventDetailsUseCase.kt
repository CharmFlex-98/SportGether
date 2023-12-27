package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventDetailsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventService: EventService
) {

    suspend operator fun invoke(eventId: Int, refresh: Boolean = false): Result<EventInfoDomainModel> {
        return if (refresh) {
           resultOf { eventRepository.fetchEventById(eventId = eventId) }
        } else {
            eventService.fetchEvents().first().map { eventPageInfo ->
                eventPageInfo.eventInfoDomainModel.first { eventInfo -> eventInfo.eventId == eventId }
            }
        }
    }
}