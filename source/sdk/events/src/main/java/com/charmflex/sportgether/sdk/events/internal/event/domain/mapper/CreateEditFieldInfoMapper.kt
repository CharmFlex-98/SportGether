package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class CreateEditFieldInfoMapper @Inject constructor(
    private val eventFieldProvider: EventFieldProvider
) : Mapper<EventInfo, List<CreateEditFieldInfo>> {
    override fun map(from: EventInfo): List<CreateEditFieldInfo> {
        return eventFieldProvider.getFieldList().map { field ->
            when (field.type) {
                CreateEditFieldInfo.FieldType.NAME -> field.copy(value = from.eventName)
                CreateEditFieldInfo.FieldType.DESTINATION -> field.copy(value = from.place)
                CreateEditFieldInfo.FieldType.START_TIME -> field.copy(value = from.startTime.toString())
                CreateEditFieldInfo.FieldType.END_TIME -> field.copy(value = from.endTime.toString())
                else -> field
            }
        }
    }
}