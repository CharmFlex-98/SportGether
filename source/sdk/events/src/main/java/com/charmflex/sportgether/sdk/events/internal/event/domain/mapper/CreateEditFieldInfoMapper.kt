package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class CreateEditFieldInfoMapper @Inject constructor(
    private val eventFieldProvider: EventFieldProvider
) : Mapper<EventInfo, List<CreateEditFieldInfo>> {
    override fun map(from: EventInfo): List<CreateEditFieldInfo> {
        return eventFieldProvider.getFieldList().map {
            when (it.type) {
                CreateEditFieldInfo.FieldType.NAME -> it.copy(value = from.eventName)
                CreateEditFieldInfo.FieldType.DESTINATION -> it.copy(value = from.place)
                CreateEditFieldInfo.FieldType.START_TIME -> it.copy(value = from.startTime.toString())
                CreateEditFieldInfo.FieldType.END_TIME -> it.copy(value = from.endTime.toString())
                CreateEditFieldInfo.FieldType.MAX_PARTICIPANT -> it.copy(value = from.maxParticipantCount.toString())
                CreateEditFieldInfo.FieldType.DESCRIPTION -> it.copy(value = from.description)
            }
        }
    }
}