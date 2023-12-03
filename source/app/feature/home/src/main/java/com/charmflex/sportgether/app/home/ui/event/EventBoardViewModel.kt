package com.charmflex.sportgether.app.home.ui.event

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.app.home.domain.usecases.GetEventBoardDetailsUseCase
import com.charmflex.sportgether.app.home.navigation.HomeNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.ui_common.ContentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventBoardViewModel @Inject constructor(
    private val eventService: EventService,
    private val getEventBoardDetailsUseCase: GetEventBoardDetailsUseCase,
    private val homeNavigator: HomeNavigator
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventBoardViewState())
    val viewState = _viewState.asStateFlow()

    init {
        refresh()
        fetchEvents()
    }

    fun refresh() {
        _viewState.update {
            it.copy(
                contentState = ContentState.LoadingState
            )
        }
        viewModelScope.launch {
            eventService.refreshEvents()
        }
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            delay(2000)
            getEventBoardDetailsUseCase().collectLatest {
                it.fold(
                    onSuccess = { eventDetail ->
                        updateEvents(eventDetail)
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
    }


    private fun updateEvents(eventDetail: List<EventBoardViewState.EventDetail>) {
        _viewState.update {
            it.copy(
                eventDetail = eventDetail,
                contentState = if (eventDetail.isEmpty()) ContentState.EmptyState else ContentState.LoadedState
            )
        }
    }

    fun onEventItemClick(eventInfo: EventBoardViewState.EventDetail) {
        homeNavigator.toEventDetailScreen(eventInfo.eventId)
    }

    fun onHostEventClick() {
        homeNavigator.toHostEventScreen()
    }
}

data class EventBoardViewState(
    val contentState: ContentState = ContentState.LoadingState,
    val errorType: UIErrorType = UIErrorType.None,
    val eventDetail: List<EventDetail> = listOf()
) {
    data class EventDetail(
        val eventId: Int,
        val eventName: String,
        val eventDate: String,
        val eventType: String,
        val eventHost: String,
        val eventStartTime: String,
        val eventEndTime: String,
        val eventDestination: String
    )
}