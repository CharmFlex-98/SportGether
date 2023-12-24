package com.charmflex.sportgether.sdk.events.internal.event.domain.repositories

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventPageInfoDomainModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.JoinEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import javax.inject.Inject

internal interface EventRepository {
    suspend fun fetchEvents(input: GetEventsInput): Result<EventPageInfoDomainModel>

    suspend fun createEvent(input: CreateEventInput): Result<Unit>

    suspend fun joinEvent(eventId: Int): Result<Unit>
}