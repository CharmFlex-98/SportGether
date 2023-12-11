package com.charmflex.sportgether.sdk.events

import android.util.Log
import com.charmflex.sportgether.sdk.core.utils.SingletonHolder
import com.charmflex.sportgether.sdk.events.internal.di.component.EventComponent
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.data.models.isLoadFirstPage
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface EventService {
    suspend fun refreshEvents(input: GetEventsInput, isFirstLoad: Boolean = true)

    fun fetchEvents(): Flow<Result<EventPageInfo>>

    companion object {
        fun getInstance(): EventService {
            return EventServiceFacade.getInstance()
        }
    }

}

internal class EventServiceFacade @Inject constructor(
    private val repository: EventRepository
) : EventService {

    private val _sharedEventInfo = MutableSharedFlow<Result<EventPageInfo>>(replay = 1)
    private val sharedEventInfo = _sharedEventInfo.asSharedFlow()

    override suspend fun refreshEvents(input: GetEventsInput, isFirstLoad: Boolean) {
        return repository.fetchEvents(input).fold(
           onSuccess = {
               val value = when (isFirstLoad) {
                   true -> Result.success(it)
                   false -> {
                       sharedEventInfo.first().map { existing ->
                           val newList = existing.eventInfo.toMutableList().apply { addAll(it.eventInfo) }
                           existing.copy(
                               eventInfo = newList,
                               nextCursorId = it.nextCursorId
                           )
                       }
                   }
               }
               _sharedEventInfo.tryEmit(value = value)
           },
           onFailure = {
               Log.d(this.javaClass.simpleName, "fetchEvent: Failed to fetch events info!")
               _sharedEventInfo.tryEmit(Result.failure(it))
           }
       )
    }

    override fun fetchEvents(): Flow<Result<EventPageInfo>> {
        return sharedEventInfo
    }

    companion object : SingletonHolder<EventService>(
        {
            EventComponent.injectCreate().getEventService()
        }
    )
}