package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailFieldInfo
import javax.inject.Inject

internal class EventDetailFieldInfoMapper @Inject constructor() : Mapper<List<CreateEditFieldInfo>, List<EventDetailFieldInfo>> {
    override fun map(from: List<CreateEditFieldInfo>): List<EventDetailFieldInfo> {
        return from.map {
            EventDetailFieldInfo(
                name = it.name,
                value = it.value
            )
        }
    }
}