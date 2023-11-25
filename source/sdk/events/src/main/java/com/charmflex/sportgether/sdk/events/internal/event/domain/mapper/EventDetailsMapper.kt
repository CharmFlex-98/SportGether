package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class EventDetailsMapper @Inject constructor(
    private val eventFieldProvider: EventFieldProvider
) : Mapper<EventInfo, List<EventDetailField>> {
    override fun map(from: EventInfo): List<EventDetailField> {
        return eventFieldProvider.getFieldList().map { field ->
            when (field.type) {
                EventDetailField.FieldType.NAME -> field.copy(value = from.eventName)
                EventDetailField.FieldType.DESTINATION -> field.copy(value = from.place)
                EventDetailField.FieldType.START_TIME -> field.copy(value = from.startTime.toString())
                EventDetailField.FieldType.END_TIME -> field.copy(value = from.endTime.toString())
                else -> field
            }
        }
    }
}