package com.charmflex.sportgether.app.home.domain.usecases

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventPageInfoDomainModel
import javax.inject.Inject

internal class GetScheduledEventsUseCase @Inject constructor(
    private val eventService: EventService
) {
    suspend operator fun invoke(): Result<ScheduledEventPageInfoDomainModel> {
        return resultOf { eventService.fetchUserEvents() }
    }
}
