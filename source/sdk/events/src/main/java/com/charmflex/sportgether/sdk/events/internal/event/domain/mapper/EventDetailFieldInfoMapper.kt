package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class EventDetailFieldInfoMapper @Inject constructor(
    private val eventFieldProvider: EventFieldProvider
) : Mapper<EventInfo, List<EventDetailFieldInfo>> {
    override fun map(from: EventInfo): List<EventDetailFieldInfo> {
        return eventFieldProvider.getFieldList().map { field ->
            when (field.type) {
                EventDetailFieldInfo.FieldType.NAME -> field.copy(value = from.eventName)
                EventDetailFieldInfo.FieldType.DESTINATION -> field.copy(value = from.place)
                EventDetailFieldInfo.FieldType.START_TIME -> field.copy(value = from.startTime.toString())
                EventDetailFieldInfo.FieldType.END_TIME -> field.copy(value = from.endTime.toString())
                else -> field
            }
        }
    }
}