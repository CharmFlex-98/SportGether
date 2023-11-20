package com.charmflex.sportgether.sdk.events

import android.media.metrics.Event
import android.util.Log
import com.charmflex.sportgether.sdk.core.utils.SingletonHolder
import com.charmflex.sportgether.sdk.events.internal.di.component.EventComponent
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface EventService {
    suspend fun refreshEvents()

    fun fetchEvents(): Flow<Result<List<EventInfo>>>

    companion object {
        fun getInstance(): EventService {
            return EventServiceFacade.getInstance()
        }
    }

}

internal class EventServiceFacade @Inject constructor(
    private val repository: EventRepository
) : EventService {

    private val _sharedEventInfo = MutableSharedFlow<Result<List<EventInfo>>>(replay = 1)
    private val sharedEventInfo = _sharedEventInfo.asSharedFlow()

    override suspend fun refreshEvents() {
        return repository.fetchEvents().fold(
           onSuccess = {
               _sharedEventInfo.tryEmit(value = Result.success(it))
           },
           onFailure = {
               Log.d(this.javaClass.simpleName, "fetchEvent: Failed to fetch events info!")
               _sharedEventInfo.tryEmit(Result.failure(it))
           }
       )
    }

    override fun fetchEvents(): Flow<Result<List<EventInfo>>> {
        return sharedEventInfo
    }

    companion object : SingletonHolder<EventService>(
        {
            EventComponent.injectCreate().getEventService()
        }
    )
}