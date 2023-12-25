package com.charmflex.sportgether.app.home.domain.usecases

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import javax.inject.Inject

internal class GetScheduledEventsUseCase @Inject constructor(
    private val eventService: EventService
) {
    suspend operator fun invoke(): Result<List<ScheduledEventInfoDomainModel>> {
        return resultOf { eventService.fetchUserEvents() }
    }
}
