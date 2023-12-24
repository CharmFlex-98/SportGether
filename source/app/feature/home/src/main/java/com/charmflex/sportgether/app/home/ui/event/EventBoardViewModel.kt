package com.charmflex.sportgether.app.home.ui.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.app.home.domain.usecases.GetEventBoardDetailsUseCase
import com.charmflex.sportgether.app.home.navigation.HomeNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.core.utils.DATE_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.TIME_ONLY_DEFAULT_PATTERN
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.data.models.GetEventsInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.ui_common.ContentState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _pageViewState = MutableStateFlow(PageViewState())
    val pageViewState = _pageViewState.asStateFlow()

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
            eventService.refreshEvents(GetEventsInput())
        }
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            delay(2000)
            eventService.fetchEvents().collectLatest {
                it.fold(
                    onSuccess = { eventPageInfo ->
                        updateEvents(eventPageInfo.eventInfoDomainModel)
                        _pageViewState.update { pageViewState ->
                            pageViewState.copy(
                                nextCursorId = eventPageInfo.nextCursorId
                            )
                        }
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

    private fun fetchNextEvents(cursorId: String) {
        _pageViewState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            delay(1000)
            eventService.refreshEvents(input = GetEventsInput(nextCursor = cursorId), isFirstLoad = false)
        }
    }


    private fun updateEvents(eventInfoDomainModel: List<EventInfoDomainModel>) {
        val eventDetails = eventInfoDomainModel.map {
            EventBoardViewState.EventDetail(
                eventId = it.eventId,
                eventName = it.eventName,
                eventDate = it.startTime.fromISOToStringWithPattern(DATE_ONLY_DEFAULT_PATTERN),
                eventType = "Badminton",
                eventHost = it.host.username,
                eventStartTime = it.startTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN),
                eventEndTime = it.endTime.fromISOToStringWithPattern(TIME_ONLY_DEFAULT_PATTERN),
                eventDestination = it.place
            )
        }
        _viewState.update {
            it.copy(
                eventDetail = eventDetails,
                contentState = if (eventInfoDomainModel.isEmpty()) ContentState.EmptyState else ContentState.LoadedState,
            )
        }
        _pageViewState.update {
            it.copy(
                nextCursorId = it.nextCursorId,
                isLoading = false
            )
        }
    }

    fun onEventItemClick(eventInfo: EventBoardViewState.EventDetail) {
        homeNavigator.toEventDetailScreen(eventInfo.eventId)
    }

    fun onHostEventClick() {
        homeNavigator.toHostEventScreen()
    }

    fun fetchMoreEvent() {
        toggleLoadingMoreState(true)
        fetchNextEvents(_pageViewState.value.nextCursorId)
    }

    private fun toggleLoadingMoreState(loadingMore: Boolean) {
        _pageViewState.update {
            it.copy(
                isLoading = loadingMore
            )
        }
    }
}

data class PageViewState(
    val isLoading: Boolean = false,
    val nextCursorId: String = "",
    val pageSize: Int = 5,
)

data class EventBoardViewState(
    val contentState: ContentState = ContentState.LoadingState,
    val errorType: UIErrorType = UIErrorType.None,
    val eventDetail: List<EventDetail> = listOf(),
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

    fun page(pageSize: Int): Int {
        return eventDetail.size / pageSize + 1
    }
}