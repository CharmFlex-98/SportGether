package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.core.utils.toLocalDateTime
import com.charmflex.sportgether.sdk.core.utils.toStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventForModifyUseCase
import com.charmflex.sportgether.sdk.ui_common.DEFAULT_DATE_TIME_PATTERN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

internal class CreateEditEventViewModel(
    private val getEventForModifyUseCase: GetEventForModifyUseCase,
    private val eventFieldProvider: EventFieldProvider,
    private val eventId: Int?
) : ViewModel() {
    private val _viewState = MutableStateFlow(CreateEditEventViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val getEventForModifyUseCase: GetEventForModifyUseCase,
        private val eventFieldProvider: EventFieldProvider,
    ) {
        fun create(eventId: Int?): CreateEditEventViewModel {
            return CreateEditEventViewModel(getEventForModifyUseCase, eventFieldProvider, eventId)
        }
    }

    init {
        if (isEdit()) loadData()
        else clearData()
    }

    private fun loadData() {
        eventId?.let {
            viewModelScope.launch {
                getEventForModifyUseCase(eventId = it).fold(
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
                    CreateEditFieldInfo.FieldType.NAME -> it.copy(nameField = field)
                    CreateEditFieldInfo.FieldType.DESCRIPTION -> it.copy(descriptionField = field)
                    CreateEditFieldInfo.FieldType.START_TIME -> it.copy(startTimeField = field)
                    CreateEditFieldInfo.FieldType.END_TIME -> it.copy(endTimeField = field)
                    CreateEditFieldInfo.FieldType.MAX_PARTICIPANT -> it.copy(maxParticipantField = field)
                    CreateEditFieldInfo.FieldType.DESTINATION -> it.copy(placeField = field)
                }
            }
        }
    }

    private fun mapData(fields: List<CreateEditFieldInfo>) {
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

    fun updateField(type: CreateEditFieldInfo.FieldType, newValue: String) {
        when (type) {
            CreateEditFieldInfo.FieldType.NAME -> updateEventName(newValue)
            CreateEditFieldInfo.FieldType.DESTINATION -> updatePlace(newValue)
            CreateEditFieldInfo.FieldType.START_TIME -> updateStartTime(newValue)
            CreateEditFieldInfo.FieldType.END_TIME -> updateEndTime(newValue)
            CreateEditFieldInfo.FieldType.MAX_PARTICIPANT -> updateMaxParticipantCount(newValue)
            CreateEditFieldInfo.FieldType.DESCRIPTION -> updateDescription(newValue)
        }
    }

    fun onChooseDate(localDateTime: LocalDateTime) {
        _viewState.update {
            it.copy(
                datePickerState = it.datePickerState.copy(cacheDateTime = localDateTime, isShowCalendar = false, isShowClock = true)
            )
        }
    }

    fun onChooseTime(hour: Int, min: Int) {
        val prevTime = _viewState.value.datePickerState.cacheDateTime
        val res = prevTime?.plusHours(hour.toLong())?.plusMinutes(min.toLong()).toStringWithPattern(
            DEFAULT_DATE_TIME_PATTERN
        )
        val isStartDate = _viewState.value.datePickerState.isStartDateChose
        _viewState.update {
            if (isStartDate) {
                it.copy(
                    datePickerState = it.datePickerState.copy(isShowClock = false),
                    startTimeField = it.startTimeField.copy(
                        value = res
                    )
                )
            } else {
                it.copy(
                    datePickerState = it.datePickerState.copy(isShowClock = false),
                    endTimeField = it.endTimeField.copy(
                        value = res
                    )
                )
            }

        }
    }

    fun toggleClock(isShow: Boolean) {
        _viewState.update {
            it.copy(
                datePickerState = it.datePickerState.copy(isShowClock = isShow)
            )
        }
    }

    fun toggleCalendar(isShow: Boolean, isStartDate: Boolean) {
        _viewState.update {
            it.copy(
                datePickerState = it.datePickerState.copy(
                    isShowCalendar = isShow,
                    isStartDateChose = isStartDate,
                    initialDateTime = when {
                        isStartDate -> it.startTimeField.value.toLocalDateTime(
                            DEFAULT_DATE_TIME_PATTERN
                        )

                        else -> it.endTimeField.value.toLocalDateTime(DEFAULT_DATE_TIME_PATTERN)
                    }
                )
            )
        }
    }
}

internal data class CreateEditEventViewState(
    val isLoading: Boolean = false,
    val nameField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val placeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val startTimeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val endTimeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val maxParticipantField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val descriptionField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val error: UIErrorType = UIErrorType.None,
    val datePickerState: DatePickerState = DatePickerState()
) {
    data class DatePickerState(
        val isShowCalendar: Boolean = false,
        val isShowClock: Boolean = false,
        val isStartDateChose: Boolean = true,
        val initialDateTime: LocalDateTime? = null,
        val cacheDateTime: LocalDateTime? = null
    )
}

internal data class CreateEditFieldInfo(
    val name: String = "",
    val hint: String = "",
    val value: String = "",
    val type: FieldType = FieldType.NAME
) {
    enum class FieldType {
        NAME, START_TIME, END_TIME, DESTINATION, MAX_PARTICIPANT, DESCRIPTION
    }
}