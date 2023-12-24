package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldPresentationModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class CreateEditFieldPresentationModelMapper @Inject constructor(
    private val eventFieldProvider: EventFieldProvider
) : Mapper<EventInfoDomainModel, List<CreateEditFieldPresentationModel>> {
    override fun map(from: EventInfoDomainModel): List<CreateEditFieldPresentationModel> {
        return eventFieldProvider.getFieldList().map {
            when (it.type) {
                CreateEditFieldPresentationModel.FieldType.NAME -> it.copy(value = from.eventName)
                CreateEditFieldPresentationModel.FieldType.DESTINATION -> it.copy(value = from.place)
                CreateEditFieldPresentationModel.FieldType.START_TIME -> it.copy(value = from.startTime.fromISOToStringWithPattern(
                    DEFAULT_DATE_TIME_PATTERN))
                CreateEditFieldPresentationModel.FieldType.END_TIME -> it.copy(value = from.endTime.fromISOToStringWithPattern(
                    DEFAULT_DATE_TIME_PATTERN
                ))
                CreateEditFieldPresentationModel.FieldType.MAX_PARTICIPANT -> it.copy(value = from.maxParticipantCount.toString())
                CreateEditFieldPresentationModel.FieldType.DESCRIPTION -> it.copy(value = from.description)
            }
        }
    }
}