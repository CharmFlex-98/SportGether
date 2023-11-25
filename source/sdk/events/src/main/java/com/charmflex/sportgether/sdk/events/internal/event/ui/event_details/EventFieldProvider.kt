package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailFieldInfo
import javax.inject.Inject

internal class EventFieldProvider @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) {

    fun getFieldList(): List<EventDetailFieldInfo> {
        return listOf(
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_name),
                hint = resourcesProvider.getString(R.string.event_detail_name_hint),
                value = "",
                type = EventDetailFieldInfo.FieldType.NAME
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_place),
                hint = resourcesProvider.getString(R.string.event_detail_place_hint),
                value = "",
                type = EventDetailFieldInfo.FieldType.DESTINATION
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                hint = "",
                value = "",
                type = EventDetailFieldInfo.FieldType.START_TIME
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                hint = "",
                value = "",
                type = EventDetailFieldInfo.FieldType.END_TIME
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                hint = resourcesProvider.getString(R.string.event_max_joiner_num_hint),
                value = "",
                type = EventDetailFieldInfo.FieldType.MAX_PARTICIPANT
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_description),
                hint = resourcesProvider.getString(R.string.event_description_hint),
                value = "",
                type = EventDetailFieldInfo.FieldType.DESCRIPTION
            )
        )
    }
}