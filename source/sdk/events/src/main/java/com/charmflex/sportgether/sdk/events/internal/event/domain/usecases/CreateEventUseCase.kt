package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import javax.inject.Inject

internal class CreateEventUseCase @Inject constructor(
    private val eventService: EventService
) {

    suspend operator fun invoke(eventInput: CreateEventInput) {
        eventService.createEvent(eventInput)
    }
}