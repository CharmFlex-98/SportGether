package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailsViewState
import javax.inject.Inject

internal class EventDetailFieldMapper @Inject constructor() : Mapper<List<EventDetailFieldInfo>, List<EventDetailsViewState.EventDetailField>> {
    override fun map(from: List<EventDetailFieldInfo>): List<EventDetailsViewState.EventDetailField> {
        return from.map {
            EventDetailsViewState.EventDetailField(
                name = it.name,
                value = it.value
            )
        }
    }
}