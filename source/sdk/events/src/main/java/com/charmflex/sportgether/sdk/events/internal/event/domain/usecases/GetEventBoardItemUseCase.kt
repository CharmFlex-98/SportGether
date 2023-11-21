package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import com.charmflex.sportgether.sdk.events.internal.event.data.mapper.EventInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository

internal class GetEventBoardItemUseCase (
    private val eventRepository: EventRepository,
    private val mapper: EventInfoMapper
) {


}