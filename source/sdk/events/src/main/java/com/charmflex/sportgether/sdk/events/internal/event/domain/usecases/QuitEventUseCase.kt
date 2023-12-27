package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import javax.inject.Inject

internal class QuitEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
){
    suspend operator fun invoke(eventId: Int): Result<Unit> {
        return resultOf { eventRepository.quitEvent(eventId = eventId) }
    }
}