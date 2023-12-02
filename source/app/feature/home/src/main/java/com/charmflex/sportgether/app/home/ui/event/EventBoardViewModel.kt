package com.charmflex.sportgether.app.home.ui.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.app.home.navigation.HomeNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.ui_common.ContentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventBoardViewModel @Inject constructor(
    private val eventService: EventService,
    private val homeNavigator: HomeNavigator
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventBoardViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            eventService.refreshEvents()
            fetchEvents()
        }
    }

    private suspend fun fetchEvents() {
        eventService.fetchEvents().collectLatest {
            it.fold(
                onSuccess = { eventInfo ->
                    updateEvents(eventInfo)
                },
                onFailure = {
                    Log.d("test", it.toString())
                    _viewState.update { state ->
                        state.copy(
                            contentState = ContentState.ErrorState
                        )
                    }
                }
            )
        }
    }

    private fun updateEvents(events: List<EventInfo>) {
        _viewState.update {
            it.copy(
                events = events,
                contentState = if (events.isEmpty()) ContentState.EmptyState else ContentState.LoadedState
            )
        }
    }

    fun onEventItemClick(eventInfo: EventInfo) {
        homeNavigator.toEventDetailScreen(eventInfo.eventId)
    }

    fun onHostEventClick() {
        homeNavigator.toHostEventScreen()
    }
}

data class EventBoardViewState(
    val contentState: ContentState = ContentState.LoadingState,
    val errorType: UIErrorType = UIErrorType.None,
    val events: List<EventInfo> = listOf()
)