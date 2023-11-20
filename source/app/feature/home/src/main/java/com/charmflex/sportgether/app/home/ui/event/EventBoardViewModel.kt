package com.charmflex.sportgether.app.home.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventInfo
import com.charmflex.sportgether.sdk.ui_common.ContentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventBoardViewModel @Inject constructor(
    private val eventService: EventService
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
}

data class EventBoardViewState(
    val contentState: ContentState = ContentState.LoadingState,
    val errorType: UIErrorType = UIErrorType.None,
    val events: List<EventInfo> = listOf()
)