package com.charmflex.sportgether.sdk.events.internal.event.data.repository

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventInfoDomainModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventPageInfoDomainModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.ScheduledEventInfoDomainModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.JoinEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import javax.inject.Inject

internal class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val eventListDomainModelMapper: EventPageInfoDomainModelMapper,
    private val eventInfoDomainModelMapper: EventInfoDomainModelMapper, 
    private val userEventsMapper: ScheduledEventInfoDomainModelMapper
) : EventRepository {

    override suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfoDomainModel> {
        return resultOf { eventListDomainModelMapper.map(eventApi.fetchAllEvents(input)) }
    }

    override suspend fun fetchUserEvents(): List<ScheduledEventInfoDomainModel> {
        return userEventsMapper.map(eventApi.fetchUserEvents())
    }

    override suspend fun fetchEventById(eventId: Int): EventInfoDomainModel {
        return eventInfoDomainModelMapper.map(
            eventApi.fetchEvent(eventId = eventId)
        )
    }

    override suspend fun createEvent(input: CreateEventInput): Result<Unit> {
        return resultOf { eventApi.createEvent(input) }
    }

    override suspend fun joinEvent(eventId: Int): Result<Unit> {
        return resultOf { eventApi.joinEvent(JoinEventInput(eventId = eventId)) }
    }

    override suspend fun quitEvent(eventId: Int) {
        return eventApi.quitEvent(eventId = eventId)
    }
}