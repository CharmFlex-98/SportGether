package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import javax.inject.Inject

internal class EventFieldProvider @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) {

    fun getFieldList(): List<CreateEditFieldPresentationModel> {
        return listOf(
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_name),
                hint = resourcesProvider.getString(R.string.event_detail_name_hint),
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.NAME
            ),
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_place),
                hint = resourcesProvider.getString(R.string.event_detail_place_hint),
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.DESTINATION
            ),
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                hint = "",
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.START_TIME
            ),
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                hint = "",
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.END_TIME
            ),
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                hint = resourcesProvider.getString(R.string.event_max_joiner_num_hint),
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.MAX_PARTICIPANT
            ),
            CreateEditFieldPresentationModel(
                name = resourcesProvider.getString(R.string.event_description),
                hint = resourcesProvider.getString(R.string.event_description_hint),
                value = "",
                type = CreateEditFieldPresentationModel.FieldType.DESCRIPTION
            )
        )
    }
}