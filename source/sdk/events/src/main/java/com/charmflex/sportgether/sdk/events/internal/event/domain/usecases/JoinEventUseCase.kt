package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import javax.inject.Inject

internal class JoinEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(eventId: Int): Result<Unit> {
        return repository.joinEvent(eventId = eventId)
    }
}