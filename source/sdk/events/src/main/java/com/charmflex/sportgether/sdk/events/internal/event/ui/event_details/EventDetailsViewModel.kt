package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.runtime.toMutableStateMap
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

    fun isEdit(): Boolean {
        return flowType == FlowType.EDIT
    }

    private fun loadEventDetails(eventId: Int) {
        viewModelScope.launch {
            eventDetailsUseCase(eventId = eventId).fold(
                onSuccess = {
                    _viewState.update { state ->
                        state.copy(
                            fields = it.associateBy { field -> field.type }.toMutableMap()
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

    private fun resetEventDetails() {
        _viewState.update {
            it.copy(
                fields = eventFieldProvider.getFieldList().associateBy { field -> field.type }.toMutableMap()
            )
        }
    }

    private fun updateValueByType(fieldType: EventDetailField.FieldType, value: String) {
        val prev = _viewState.value.fields[fieldType]
        val next = prev?.copy(value = value)

        next?.let {
            _viewState.update { state ->
                state.copy(
                    fields = state.fields.apply { this[fieldType] = next }
                )
            }
        }
    }

    fun onEdit(fieldType: EventDetailField.FieldType, value: String) {
        updateValueByType(fieldType, value)
    }

    internal enum class FlowType {
        CREATE, EDIT, VIEW
    }
}

internal data class EventDetailsViewState(
    val fields: MutableMap<EventDetailField.FieldType, EventDetailField> = mutableMapOf(),
    val isLoading: Boolean = false,
    val errorType: UIErrorType = UIErrorType.None
)
