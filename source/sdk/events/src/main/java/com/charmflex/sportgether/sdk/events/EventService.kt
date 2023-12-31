package com.charmflex.sportgether.sdk.events

import android.content.Context
import android.util.Log
import com.charmflex.sportgether.sdk.core.utils.SingletonHolder
import com.charmflex.sportgether.sdk.events.internal.di.component.EventComponent
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventPageInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface EventService {
    suspend fun refreshEvents(input: GetEventsInput, isFirstLoad: Boolean = true)

    fun fetchEvents(): Flow<Result<EventPageInfoDomainModel>>

    suspend fun fetchUserEvents(): List<ScheduledEventInfoDomainModel>

    companion object {
        fun getInstance(context: Context): EventService {
            return EventServiceFacade.getInstance(context)
        }
    }
}

internal class EventServiceFacade @Inject constructor(
    private val repository: EventRepository
) : EventService {

    private val _sharedEventInfo = MutableSharedFlow<Result<EventPageInfoDomainModel>>(replay = 1)
    private val sharedEventInfo = _sharedEventInfo.asSharedFlow()

    override suspend fun refreshEvents(input: GetEventsInput, isFirstLoad: Boolean) {
        return repository.fetchEvents(input).fold(
           onSuccess = {
               val value = when (isFirstLoad) {
                   true -> Result.success(it)
                   false -> {
                       sharedEventInfo.first().map { existing ->
                           val newList = existing.eventInfoDomainModel.toMutableList().apply { addAll(it.eventInfoDomainModel) }
                           existing.copy(
                               eventInfoDomainModel = newList,
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

    override fun fetchEvents(): Flow<Result<EventPageInfoDomainModel>> {
        return sharedEventInfo
    }

    override suspend fun fetchUserEvents(): List<ScheduledEventInfoDomainModel> {
        return repository.fetchUserEvents()
    }

    companion object : SingletonHolder<Context, EventService>(
        {
            EventComponent.injectCreate(it).getEventService()
        }
    )
}