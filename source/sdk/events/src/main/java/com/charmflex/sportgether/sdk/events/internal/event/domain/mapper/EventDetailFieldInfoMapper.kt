package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import androidx.compose.ui.res.stringResource
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventFieldProvider
import javax.inject.Inject

internal class EventDetailFieldInfoMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) : Mapper<EventInfo, List<EventDetailFieldInfo>> {

    override fun map(from: EventInfo): List<EventDetailFieldInfo> {
        return listOf(
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_name),
                value = from.eventName
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_place),
                value = from.place
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                value = from.startTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                value = from.endTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                value = from.maxParticipantCount.toString()
            ),
            EventDetailFieldInfo(
                name = resourcesProvider.getString(R.string.event_description),
                value = from.description
            )
        )
    }
}