package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.EventDetailFieldInfoMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDetailsViewModel(
    private val eventDetailsUseCase: GetEventDetailsUseCase,
    private val fieldMapper: EventDetailFieldInfoMapper,
    private val eventId: Int,
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventDetailsViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val eventDetailsUseCase: GetEventDetailsUseCase,
        private val fieldMapper: EventDetailFieldInfoMapper,
        ) {
        fun create(eventId: Int?): EventDetailsViewModel {
            val id = checkNotNull(eventId)

            return EventDetailsViewModel(
                eventDetailsUseCase,
                fieldMapper,
                id
            )
        }
    }

    init {
        refresh()
    }

    private fun refresh() {
        loadEventDetails(eventId)
    }

    private fun loadEventDetails(eventId: Int) {
        viewModelScope.launch {
            eventDetailsUseCase(eventId = eventId).fold(
                onSuccess = {
                    _viewState.update { state ->
                        state.copy(
                            fields = fieldMapper.map(it)
                        )
                    }
                },
                onFailure = {
                    // TODO:
                }
            )
        }
    }

    fun onPrimaryButtonClick() {

    }
}

internal data class EventDetailsViewState(
    val fields: List<EventDetailFieldInfo> = listOf(),
    val isLoading: Boolean = false,
    val errorType: UIErrorType = UIErrorType.None
)

internal data class EventDetailFieldInfo(
    val name: String,
    val value: String
)
