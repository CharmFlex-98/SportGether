package com.charmflex.sportgether.sdk.events.internal.event.data.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsResponse
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import javax.inject.Inject

internal class EventPageInfoDomainModelMapper @Inject constructor(
    private val mapper: EventInfoDomainModelMapper
)  : Mapper<GetEventsResponse, EventPageInfoDomainModel> {
    override fun map(from: GetEventsResponse): EventPageInfoDomainModel {
        val events = from.events.map { mapper.map(it) }

        return EventPageInfoDomainModel(
            eventInfoDomainModel = events,
            nextCursorId = from.nextCursorId
        )
    }
}