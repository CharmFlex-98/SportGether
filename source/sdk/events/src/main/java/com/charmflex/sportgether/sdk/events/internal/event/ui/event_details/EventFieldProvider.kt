package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import javax.inject.Inject

internal class EventFieldProvider @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) {

    fun getFieldList(): List<CreateEditFieldInfo> {
        return listOf(
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_name),
                hint = resourcesProvider.getString(R.string.event_detail_name_hint),
                value = "",
                type = CreateEditFieldInfo.FieldType.NAME
            ),
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_place),
                hint = resourcesProvider.getString(R.string.event_detail_place_hint),
                value = "",
                type = CreateEditFieldInfo.FieldType.DESTINATION
            ),
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                hint = "",
                value = "",
                type = CreateEditFieldInfo.FieldType.START_TIME
            ),
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                hint = "",
                value = "",
                type = CreateEditFieldInfo.FieldType.END_TIME
            ),
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                hint = resourcesProvider.getString(R.string.event_max_joiner_num_hint),
                value = "",
                type = CreateEditFieldInfo.FieldType.MAX_PARTICIPANT
            ),
            CreateEditFieldInfo(
                name = resourcesProvider.getString(R.string.event_description),
                hint = resourcesProvider.getString(R.string.event_description_hint),
                value = "",
                type = CreateEditFieldInfo.FieldType.DESCRIPTION
            )
        )
    }
}