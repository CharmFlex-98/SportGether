package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.CreateEditFieldInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditFieldInfo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetEventDetailsUseCase @Inject constructor(
    private val eventService: EventService,
    private val mapper: CreateEditFieldInfoMapper
) {
    suspend operator fun invoke(eventId: Int): Result<List<CreateEditFieldInfo>> {
        return eventService.fetchEvents().first().fold(
            onSuccess = { eventInfoList ->
                val res = eventInfoList.first { eventInfo -> eventInfo.eventId == eventId }
                return@fold Result.success(mapper.map(res))
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}