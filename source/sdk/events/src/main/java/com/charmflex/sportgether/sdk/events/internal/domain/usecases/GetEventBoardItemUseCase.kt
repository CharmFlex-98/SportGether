package com.charmflex.sportgether.sdk.events.internal.domain.usecases

import com.charmflex.sportgether.sdk.events.internal.data.mapper.EventInfoMapper
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepository

internal class GetEventBoardItemUseCase (
    private val eventRepository: EventRepository,
    private val mapper: EventInfoMapper
) {


}