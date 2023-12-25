package com.charmflex.sportgether.app.home.domain.mappers
import com.charmflex.sportgether.app.home.ui.schedule.ScheduleEventPresentationModel
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.TIME_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.fromISOToLocalDateTime
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

// TODO: To delete 
internal class ScheduledEventsPresentationModelMapper @Inject constructor(

) : Mapper<List<EventInfoDomainModel>, List<ScheduleEventPresentationModel>>{
    override fun map(from: List<EventInfoDomainModel>): List<ScheduleEventPresentationModel> {
        return from.map {
            val startTime = it.startTime.fromISOToLocalDateTime()
            val endTime = it.endTime.fromISOToLocalDateTime()
            ScheduleEventPresentationModel(
                eventId = it.eventId,
                eventTitle = it.eventName,
                remainingDaysToStart = startTime.compareTo(endTime).toDuration(DurationUnit.DAYS).toInt(DurationUnit.DAYS),
                startTime = it.startTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN)
            )
        }
    }
}