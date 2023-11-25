package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailFieldInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CreateEditEventViewModel(
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val eventFieldProvider: EventFieldProvider,
    private val eventId: Int?
) : ViewModel() {
    private val _viewState = MutableStateFlow(CreateEditEventViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val getEventDetailsUseCase: GetEventDetailsUseCase,
        private val eventFieldProvider: EventFieldProvider,
    ) {
        fun create(eventId: Int?): CreateEditEventViewModel {
            return CreateEditEventViewModel(getEventDetailsUseCase, eventFieldProvider, eventId)
        }
    }

    init {
        if (isEdit()) loadData()
        else clearData()
    }

    private fun loadData() {
        eventId?.let {
            viewModelScope.launch {
                getEventDetailsUseCase(eventId = it).fold(
                    onSuccess = ::mapData,
                    onFailure = {}
                )
            }
        }
    }

    private fun clearData() {
        eventFieldProvider.getFieldList().forEach { field ->
            _viewState.update {
                when (field.type) {
                    EventDetailFieldInfo.FieldType.NAME -> it.copy(nameField = field)
                    EventDetailFieldInfo.FieldType.DESCRIPTION -> it.copy(descriptionField = field)
                    EventDetailFieldInfo.FieldType.START_TIME -> it.copy(startTimeField = field)
                    EventDetailFieldInfo.FieldType.END_TIME -> it.copy(endTimeField = field)
                    EventDetailFieldInfo.FieldType.MAX_PARTICIPANT -> it.copy(maxParticipantField = field)
                    EventDetailFieldInfo.FieldType.DESTINATION -> it.copy(placeField = field)
                }
            }
        }
    }

    private fun mapData(fields: List<EventDetailFieldInfo>) {
        fields.forEach {
            updateField(it.type, it.value)
        }
    }

    fun isEdit(): Boolean {
        return eventId != null
    }

    fun onClickEdit() {

    }

    private fun updateEventName(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.nameField.copy(value = newValue)
            )
        }
    }

    private fun updatePlace(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.placeField.copy(value = newValue)
            )
        }
    }

    private fun updateStartTime(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.startTimeField.copy(value = newValue)
            )
        }
    }

    private fun updateEndTime(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.endTimeField.copy(value = newValue)
            )
        }
    }

    private fun updateMaxParticipantCount(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.maxParticipantField.copy(value = newValue)
            )
        }
    }

    private fun updateDescription(newValue: String) {
        _viewState.update {
            it.copy(
                nameField = it.descriptionField.copy(value = newValue)
            )
        }
    }

    fun updateField(type: EventDetailFieldInfo.FieldType, newValue: String) {
        when (type) {
            EventDetailFieldInfo.FieldType.NAME -> updateEventName(newValue)
            EventDetailFieldInfo.FieldType.DESTINATION -> updatePlace(newValue)
            EventDetailFieldInfo.FieldType.START_TIME -> updateStartTime(newValue)
            EventDetailFieldInfo.FieldType.END_TIME -> updateEndTime(newValue)
            EventDetailFieldInfo.FieldType.MAX_PARTICIPANT -> updateMaxParticipantCount(newValue)
            EventDetailFieldInfo.FieldType.DESCRIPTION -> updateDescription(newValue)
        }
    }
}

internal data class CreateEditEventViewState(
    val isLoading: Boolean = false,
    val nameField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val placeField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val startTimeField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val endTimeField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val maxParticipantField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val descriptionField: EventDetailFieldInfo = EventDetailFieldInfo(),
    val error: UIErrorType = UIErrorType.None
)