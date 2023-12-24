package com.charmflex.sportgether.sdk.events.internal.event.data.repository

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventPageInfoDomainModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.JoinEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import javax.inject.Inject

internal class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val mapper: EventPageInfoDomainModelMapper
) : EventRepository {

    override suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfoDomainModel> {
        return resultOf { mapper.map(eventApi.fetchAllEvents(input)) }
    }

    override suspend fun createEvent(input: CreateEventInput): Result<Unit> {
        return resultOf { eventApi.createEvent(input) }
    }

    override suspend fun joinEvent(eventId: Int): Result<Unit> {
        return resultOf { eventApi.joinEvent(JoinEventInput(eventId = eventId)) }
    }
}