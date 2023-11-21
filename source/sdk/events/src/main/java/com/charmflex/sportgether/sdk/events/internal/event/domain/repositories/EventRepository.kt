package com.charmflex.sportgether.sdk.events.internal.event.domain.repositories

import com.charmflex.sportgether.sdk.core.utils.resultOf
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import javax.inject.Inject

interface EventRepository {
    suspend fun fetchEvents(): Result<List<EventInfo>>
}
internal class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val mapper: EventInfoMapper
) : EventRepository {

    override suspend fun fetchEvents(): Result<List<EventInfo>> {
        return resultOf { mapper.map(eventApi.fetchAllEvents()) }
    }
}