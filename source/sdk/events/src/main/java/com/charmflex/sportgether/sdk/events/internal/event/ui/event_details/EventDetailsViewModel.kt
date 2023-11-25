package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDetailsViewModel(
    private val eventDetailsUseCase: GetEventDetailsUseCase,
    private val eventFieldProvider: EventFieldProvider,
    private val eventId: Int?,
    private val flowType: FlowType
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventDetailsViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val eventDetailsUseCase: GetEventDetailsUseCase,
        private val eventFieldProvider: EventFieldProvider
    ) {
        fun create(eventId: Int?, isEdit: Boolean): EventDetailsViewModel {
            val flowType = when {
                isEdit -> FlowType.EDIT
                eventId == null -> FlowType.CREATE
                else -> FlowType.VIEW
            }

            return EventDetailsViewModel(
                eventDetailsUseCase,
                eventFieldProvider,
                eventId,
                flowType
            )
        }
    }

    init {
        if (flowType == FlowType.CREATE) {
            resetEventDetails()
        } else {
            eventId?.let { loadEventDetails(eventId) }
        }
    }

    private fun loadEventDetails(eventId: Int) {
        viewModelScope.launch {
            eventDetailsUseCase(eventId = eventId).fold(
                onSuccess = {
                    _viewState.update { state ->
                        state.copy(
                            fields = it
                        )
                    }
                },
                onFailure = {
                    // TODO:
                }
            )
        }
    }

    private fun resetEventDetails() {
        _viewState.update {
            it.copy(
                fields = eventFieldProvider.getFieldList()
            )
        }
    }

    private fun updateEventName(value: String) {
        _viewState.update {
            it.copy(
                fields = it.fields.map { field ->
                    if (field.type == EventDetailField.FieldType.NAME) {
                        field.copy(value = value)
                    } else {
                        field
                    }
                }
            )
        }
    }

    private fun updateDestination(value: String) {
        _viewState.update {
            it.copy(
                fields = it.fields.map { field ->
                    if (field.type == EventDetailField.FieldType.DESTINATION) {
                        field.copy(value = value)
                    } else {
                        field
                    }
                }
            )
        }
    }

    private fun updateStartTime(value: String) {
        _viewState.update {
            it.copy(
                fields = it.fields.map { field ->
                    if (field.type == EventDetailField.FieldType.START_TIME) {
                        field.copy(value = value)
                    } else {
                        field
                    }
                }
            )
        }
    }

    private fun updateEndTime(value: String) {
        _viewState.update {
            it.copy(
                fields = it.fields.map { field ->
                    if (field.type == EventDetailField.FieldType.END_TIME) {
                        field.copy(value = value)
                    } else {
                        field
                    }
                }
            )
        }
    }

    fun onEdit(fieldType: EventDetailField.FieldType, value: String) {
        when (fieldType) {
            EventDetailField.FieldType.NAME -> updateEventName(value)
            EventDetailField.FieldType.DESTINATION -> updateDestination(value)
            EventDetailField.FieldType.START_TIME -> updateStartTime(value)
            EventDetailField.FieldType.END_TIME -> updateEndTime(value)
        }
    }

    internal enum class FlowType {
        CREATE, EDIT, VIEW
    }
}

internal data class EventDetailsViewState(
    val fields: List<EventDetailField> = listOf(),
    val isLoading: Boolean = false,
    val errorType: UIErrorType = UIErrorType.None
)
