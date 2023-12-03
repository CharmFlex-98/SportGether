package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.core.utils.toISO8601String
import com.charmflex.sportgether.sdk.core.utils.toLocalDateTime
import com.charmflex.sportgether.sdk.core.utils.toStringWithPattern
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventForModifyUseCase
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import com.charmflex.sportgether.sdk.ui_common.DEFAULT_DATE_TIME_PATTERN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

internal class CreateEditEventViewModel(
    private val repository: EventRepository,
    private val getEventForModifyUseCase: GetEventForModifyUseCase,
    private val eventFieldProvider: EventFieldProvider,
    private val eventId: Int?,
    private val routeNavigator: RouteNavigator
) : ViewModel() {
    private val _viewState = MutableStateFlow(CreateEditEventViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val repository: EventRepository,
        private val getEventForModifyUseCase: GetEventForModifyUseCase,
        private val eventFieldProvider: EventFieldProvider,
        private val routeNavigator: RouteNavigator
    ) {
        fun create(eventId: Int?): CreateEditEventViewModel {
            return CreateEditEventViewModel(
                repository,
                getEventForModifyUseCase,
                eventFieldProvider,
                eventId,
                routeNavigator
            )
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

    fun back() {
        routeNavigator.popWithArguments(
            mapOf(EventRoutes.Args.SHOULD_REFRESH to true)
        )
    }

    fun onPrimaryActionClick() {
        if (isEdit()) updateEvent()
        else submitEvent()
    }

    private fun updateEvent() {
    }

    private fun submitEvent() {
        val startTimestamp = _viewState.value.startTimeField.value.toLocalDateTime(
            DEFAULT_DATE_TIME_PATTERN).toISO8601String(ZoneId.systemDefault())
        val endTimeStamp = _viewState.value.endTimeField.value.toLocalDateTime(
            DEFAULT_DATE_TIME_PATTERN).toISO8601String(ZoneId.systemDefault())

        val createEventInput = CreateEventInput(
            eventTitle = _viewState.value.nameField.value,
            startTime = startTimestamp,
            endTime = endTimeStamp,
            destination = _viewState.value.placeField.value,
            description = _viewState.value.descriptionField.value,
            eventType = "Badminton",
            maxParticipantCount = _viewState.value.maxParticipantField.value.toInt()
        )
        viewModelScope.launch {
            repository.createEvent(createEventInput).fold(
                onSuccess = {
                    _viewState.update {
                        it.copy(state = CreateEditEventViewState.State.Success)
                    }
                },
                onFailure = {
                    _viewState.update {
                        it.copy(state = CreateEditEventViewState.State.Error)
                    }
                }
            )
        }

        _viewState.update {
            it.copy(
                state = CreateEditEventViewState.State.Loading
            )
        }
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
                placeField = it.placeField.copy(value = newValue)
            )
        }
    }

    private fun updateStartTime(newValue: String) {
        _viewState.update {
            it.copy(
                startTimeField = it.startTimeField.copy(value = newValue)
            )
        }
    }

    private fun updateEndTime(newValue: String) {
        _viewState.update {
            it.copy(
                endTimeField = it.endTimeField.copy(value = newValue)
            )
        }
    }

    private fun updateMaxParticipantCount(newValue: String) {
        _viewState.update {
            it.copy(
                maxParticipantField = it.maxParticipantField.copy(value = newValue)
            )
        }
    }

    private fun updateDescription(newValue: String) {
        _viewState.update {
            it.copy(
                descriptionField = it.descriptionField.copy(value = newValue)
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
                datePickerState = it.datePickerState.copy(
                    cacheDateTime = localDateTime,
                    isShowCalendar = false,
                    isShowClock = true
                )
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
    val state: State = State.Default,
    val nameField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val placeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val startTimeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val endTimeField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val maxParticipantField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val descriptionField: CreateEditFieldInfo = CreateEditFieldInfo(),
    val error: UIErrorType = UIErrorType.None,
    val datePickerState: DatePickerState = DatePickerState()
) {
    sealed interface State {
        object Default: State
        object Loading: State
        object Error: State
        object Success: State
    }

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