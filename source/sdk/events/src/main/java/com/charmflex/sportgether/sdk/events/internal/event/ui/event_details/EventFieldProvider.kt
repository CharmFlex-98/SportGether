package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import javax.inject.Inject

internal class EventFieldProvider @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) {

    fun getFieldList(): List<EventDetailField> {
        return listOf(
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_detail_name),
                hint = resourcesProvider.getString(R.string.event_detail_name_hint),
                value = "",
                type = EventDetailField.FieldType.NAME
            ),
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_detail_place),
                hint = resourcesProvider.getString(R.string.event_detail_place_hint),
                value = "",
                type = EventDetailField.FieldType.DESTINATION
            ),
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                hint = "",
                value = "",
                type = EventDetailField.FieldType.START_TIME
            ),
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                hint = "",
                value = "",
                type = EventDetailField.FieldType.END_TIME
            ),
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                hint = resourcesProvider.getString(R.string.event_max_joiner_num_hint),
                value = "",
                type = EventDetailField.FieldType.MAX_PARTICIPANT
            ),
            EventDetailField(
                name = resourcesProvider.getString(R.string.event_description),
                hint = resourcesProvider.getString(R.string.event_description_hint),
                value = "",
                type = EventDetailField.FieldType.DESCRIPTION
            )
        )
    }
}