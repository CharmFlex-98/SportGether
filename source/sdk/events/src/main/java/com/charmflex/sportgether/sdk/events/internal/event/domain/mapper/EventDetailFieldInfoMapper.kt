package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailContentInfo
import javax.inject.Inject

internal class EventDetailFieldInfoMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) : Mapper<EventInfo, List<EventDetailContentInfo>> {

    override fun map(from: EventInfo): List<EventDetailContentInfo> {
        return listOf(
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_detail_name),
                value = from.eventName
            ),
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_detail_place),
                value = from.place
            ),
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                value = from.startTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                value = from.endTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                value = from.maxParticipantCount.toString()
            ),
            EventDetailContentInfo(
                name = resourcesProvider.getString(R.string.event_description),
                value = from.description
            )
        )
    }
}