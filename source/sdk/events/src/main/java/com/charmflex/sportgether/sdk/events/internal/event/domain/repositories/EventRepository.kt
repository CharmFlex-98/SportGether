package com.charmflex.sportgether.sdk.events.internal.event.domain.repositories

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventPageInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.JoinEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfo
import javax.inject.Inject

internal interface EventRepository {
    suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfo>

    suspend fun createEvent(input: CreateEventInput): Result<Unit>

    suspend fun joinEvent(eventId: Int): Result<Unit>
}
internal class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val mapper: EventPageInfoMapper
) : EventRepository {

    override suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfo> {
        return resultOf { mapper.map(eventApi.fetchAllEvents(input)) }
    }

    override suspend fun createEvent(input: CreateEventInput): Result<Unit> {
        return resultOf { eventApi.createEvent(input) }
    }

    override suspend fun joinEvent(eventId: Int): Result<Unit> {
        return resultOf { eventApi.joinEvent(JoinEventInput(eventId = eventId)) }
    }
}