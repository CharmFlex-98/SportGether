package com.charmflex.sportgether.app.home.domain.usecases

import com.charmflex.sportgether.app.home.domain.mappers.EventBoardDetailsMapper
import com.charmflex.sportgether.app.home.ui.event.EventBoardViewState
import com.charmflex.sportgether.sdk.events.EventService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.lang.Exception
import javax.inject.Inject

class GetEventBoardDetailsUseCase @Inject constructor(
    private val eventService: EventService,
    private val eventBoardDetailsMapper: EventBoardDetailsMapper
) {

    operator fun invoke(): Flow<Result<List<EventBoardViewState.EventDetail>>> {
        return eventService.fetchEvents().map {
            it.map { eventInfos ->
                eventInfos.eventInfo.map { eventInfo ->
                    eventBoardDetailsMapper.map(eventInfo)
                }
            }
        }
    }
}