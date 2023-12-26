package com.charmflex.sportgether.app.home.ui.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.app.home.domain.usecases.GetScheduledEventsUseCase
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import com.charmflex.sportgether.sdk.ui_common.ContentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ScheduledEventBoardViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val getScheduleEventsUseCase: GetScheduledEventsUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow(ScheduledEventViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            load()
        }
    }

    private suspend fun load() {
        getScheduleEventsUseCase().fold(
            onSuccess = {
                _viewState.update { viewState ->
                    viewState.copy(
                        scheduleEvents = it,
                        contentState = ContentState.LoadedState
                    )
                }
            },
            onFailure = {
                Log.d("test", it.message.toString())
                _viewState.update {
                    it.copy(
                        contentState = ContentState.ErrorState
                    )
                }
            }
        )
    }

    fun refresh() {
        viewModelScope.launch { load() }
    }

    fun navigateToEventDetail(eventId: Int) {
        routeNavigator.navigateTo(EventRoutes.eventDetailsDestination(eventId = eventId, refresh = true))
    }
}

internal data class ScheduledEventViewState(
    val scheduleEvents: List<ScheduledEventInfoDomainModel> = listOf(),
    val isLoading: Boolean = false,
    val contentState: ContentState = ContentState.EmptyState
)

internal data class ScheduleEventPresentationModel(
    val eventId: Int,
    val eventTitle: String,
    val startTime: String,
    val remainingDaysToStart: Int,
)