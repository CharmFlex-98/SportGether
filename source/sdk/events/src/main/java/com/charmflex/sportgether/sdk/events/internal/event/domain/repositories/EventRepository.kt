package com.charmflex.sportgether.sdk.events.internal.event.domain.repositories

import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel

internal interface EventRepository {
    suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfoDomainModel>

    suspend fun fetchUserEvents(): List<ScheduledEventInfoDomainModel>

    suspend fun fetchEventById(eventId: Int): EventInfoDomainModel

    suspend fun createEvent(input: CreateEventInput): Result<Unit>

    suspend fun joinEvent(eventId: Int): Result<Unit>

    suspend fun quitEvent(eventId: Int)
}