package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.core.utils.toISO8601String
import com.charmflex.sportgether.sdk.core.utils.toLocalDateTime
import com.charmflex.sportgether.sdk.core.utils.toStringWithPattern
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.data.models.CreateEventInput
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.CreateEditFieldPresentationModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import com.charmflex.sportgether.sdk.events.internal.place.PlaceAutoCompleteExecutor
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

internal class CreateEditEventViewModel(
    private val repository: EventRepository,
    private val getEventDetailUseCase: GetEventDetailsUseCase,
    private val mapper: CreateEditFieldPresentationModelMapper,
    private val eventFieldProvider: EventFieldProvider,
    private val eventId: Int?,
    private val routeNavigator: RouteNavigator,
    private val placeAutoCompleteExecutor: PlaceAutoCompleteExecutor,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    private val _viewState = MutableStateFlow(CreateEditEventViewState())
    val viewState = _viewState.asStateFlow()

    private val _destinationQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val destinationQuery =
        _destinationQuery.transform {
            if (showSearchBottomSheet()) {
                emit(it)
            }
        }.debounce(500)
    private var selectedPlaceId: String = ""

    class Factory @Inject constructor(
        private val repository: EventRepository,
        private val getEventDetailUseCase: GetEventDetailsUseCase,
        private val mapper: CreateEditFieldPresentationModelMapper,
        private val eventFieldProvider: EventFieldProvider,
        private val routeNavigator: RouteNavigator,
        private val placeAutoCompleteExecutor: PlaceAutoCompleteExecutor,
        private val resourcesProvider: ResourcesProvider
    ) {
        fun create(eventId: Int?): CreateEditEventViewModel {
            return CreateEditEventViewModel(
                repository,
                getEventDetailUseCase,
                mapper,
                eventFieldProvider,
                eventId,
                routeNavigator,
                placeAutoCompleteExecutor,
                resourcesProvider
            )
        }
    }

    init {
        if (isEdit()) loadData()
        else clearData()

        observeDestinationQueryEvent()
    }

    private fun showSearchBottomSheet() =
        _viewState.value.bottomSheetState is CreateEditEventViewState.BottomSheetState.SearchState

    private fun observeDestinationQueryEvent() {
        viewModelScope.launch {
            destinationQuery.collectLatest {
                if (it.isNotEmpty()) {
                    queryDestAutoComplete(it)
                } else {
                    _viewState.update { viewState ->
                        viewState.copy(
                            bottomSheetState = CreateEditEventViewState.BottomSheetState.SearchState(
                                searchKey = it,
                                options = listOf(),
                                isError = false
                            )
                        )
                    }
                }
            }
        }
    }

    private fun queryDestAutoComplete(query: String) {
        placeAutoCompleteExecutor.onQuery(
            searchQuery = query,
            onFailure = {
                when (val state = _viewState.value.bottomSheetState) {
                    is CreateEditEventViewState.BottomSheetState.SearchState -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                bottomSheetState = CreateEditEventViewState.BottomSheetState.SearchState(
                                    searchKey = state.searchKey,
                                    options = listOf(),
                                    isError = true
                                )
                            )
                        }
                    }

                    else -> {}
                }
            }
        ) {
            when (val state = _viewState.value.bottomSheetState) {
                is CreateEditEventViewState.BottomSheetState.SearchState -> {
                    _viewState.update { viewState ->
                        viewState.copy(
                            bottomSheetState = CreateEditEventViewState.BottomSheetState.SearchState(
                                searchKey = state.searchKey,
                                options = it,
                                isError = false
                            )
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun loadData() {
        eventId?.let {
            viewModelScope.launch {
                getEventDetailUseCase(eventId = it).fold(
                    onSuccess = { mapData(mapper.map(it)) },
                    onFailure = {}
                )
            }
        }
    }

    private fun clearData() {
        eventFieldProvider.getFieldList().forEach { field ->
            _viewState.update {
                when (field.type) {
                    CreateEditFieldPresentationModel.FieldType.NAME -> it.copy(nameField = field)
                    CreateEditFieldPresentationModel.FieldType.DESCRIPTION -> it.copy(
                        descriptionField = field
                    )

                    CreateEditFieldPresentationModel.FieldType.START_TIME -> it.copy(startTimeField = field)
                    CreateEditFieldPresentationModel.FieldType.END_TIME -> it.copy(endTimeField = field)
                    CreateEditFieldPresentationModel.FieldType.MAX_PARTICIPANT -> it.copy(
                        maxParticipantField = field
                    )

                    CreateEditFieldPresentationModel.FieldType.DESTINATION -> it.copy(placeField = field)
                }
            }
        }
    }

    private fun mapData(fields: List<CreateEditFieldPresentationModel>) {
        fields.forEach {
            updateField(it.type, it.value)
        }
    }

    fun isEdit(): Boolean {
        return eventId != null
    }

    fun back() {
        routeNavigator.popWithArguments(
            mapOf(
                EventRoutes.Args.SHOULD_REFRESH to true,
                EventRoutes.Args.SHOULD_REFRESH_SCHEDULED to true
            )
        )
    }

    fun onPrimaryActionClick() {
        if (isEdit()) updateEvent()
        else submitEvent()
    }

    private fun updateEvent() {
    }

    private fun submitEvent() {
        // Toggle loading state
        _viewState.update {
            it.copy(
                state = CreateEditEventViewState.State.Loading
            )
        }

        if (!isSubmissionValid()) {
            _viewState.update {
                it.copy(
                    state = CreateEditEventViewState.State.Default,
                    error = UIErrorType.SnackBarMessageError(resourcesProvider.getString(R.string.event_creation_error_snackbar_message))
                )
            }
            return
        }

        // Process
        val startTimestamp = _viewState.value.startTimeField.value.toLocalDateTime(
            DEFAULT_DATE_TIME_PATTERN
        ).toISO8601String(ZoneId.systemDefault())
        val endTimeStamp = _viewState.value.endTimeField.value.toLocalDateTime(
            DEFAULT_DATE_TIME_PATTERN
        ).toISO8601String(ZoneId.systemDefault())

        if (selectedPlaceId.isNotEmpty()) {
            placeAutoCompleteExecutor.onFetchPlaceDetails(
                selectedPlaceId,
                onFailure = {
                    _viewState.update {
                        it.copy(
                            error = UIErrorType.GenericError
                        )
                    }
                }
            ) {
                val createEventInput = CreateEventInput(
                    eventName = _viewState.value.nameField.value,
                    startTime = startTimestamp,
                    endTime = endTimeStamp,
                    destination = _viewState.value.placeField.value,
                    description = _viewState.value.descriptionField.value,
                    eventType = EventType.BADMINTON.toString(),
                    maxParticipantCount = _viewState.value.maxParticipantField.value.toIntOrNull() ?: 0
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
            }
        }
    }

    private fun isSubmissionValid(): Boolean {
        _viewState.value.let {
            if (it.nameField.value.isEmpty()) return false
            if (it.placeField.value.isEmpty()) return false
            if (it.startTimeField.value.isEmpty()) return false
            if (it.endTimeField.value.isEmpty()) return false
            if (it.maxParticipantField.value.isEmpty()) return false
            return true
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

    fun updateField(type: CreateEditFieldPresentationModel.FieldType, newValue: String) {
        when (type) {
            CreateEditFieldPresentationModel.FieldType.NAME -> updateEventName(newValue)
            CreateEditFieldPresentationModel.FieldType.DESTINATION -> updatePlace(newValue)
            CreateEditFieldPresentationModel.FieldType.START_TIME -> updateStartTime(newValue)
            CreateEditFieldPresentationModel.FieldType.END_TIME -> updateEndTime(newValue)
            CreateEditFieldPresentationModel.FieldType.MAX_PARTICIPANT -> updateMaxParticipantCount(
                newValue
            )

            CreateEditFieldPresentationModel.FieldType.DESCRIPTION -> updateDescription(newValue)
        }
    }

    fun onTapDestField() {
        _viewState.update {
            it.copy(
                bottomSheetState = CreateEditEventViewState.BottomSheetState.SearchState()
            )
        }
    }

    fun onSuggestedDestSelected(placeId: String, placeAddress: String) {
        selectedPlaceId = placeId
        _viewState.update {
            it.copy(
                placeField = it.placeField.copy(
                    value = placeAddress
                ),
                bottomSheetState = CreateEditEventViewState.BottomSheetState.None
            )
        }
    }

    fun updateSearchKey(key: String) {
        _destinationQuery.value = key
        when (val state = _viewState.value.bottomSheetState) {
            is CreateEditEventViewState.BottomSheetState.SearchState -> {
                _viewState.update {
                    it.copy(
                        bottomSheetState = CreateEditEventViewState.BottomSheetState.SearchState(
                            searchKey = key,
                            options = state.options
                        )
                    )
                }
            }

            else -> {}
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
        val res =
            prevTime?.plusHours(hour.toLong())?.plusMinutes(min.toLong())
        val resString = res.toStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
        val isStartDate = _viewState.value.datePickerState.isStartDateChose
        val start = _viewState.value.startTimeField.value
        val end = _viewState.value.endTimeField.value

        _viewState.update {
            if (isStartDate) {
                it.copy(
                    datePickerState = it.datePickerState.copy(isShowClock = false),
                    startTimeField = it.startTimeField.copy(
                        value = resString,
                        error = if (end.toLocalDateTime(DEFAULT_DATE_TIME_PATTERN)
                                ?.isBefore(res) == true
                        ) {
                            resourcesProvider.getString(R.string.start_date_error)
                        } else null
                    ),
                    endTimeField = it.endTimeField.copy(
                        error = if (start.toLocalDateTime(DEFAULT_DATE_TIME_PATTERN)
                                ?.isAfter(res) == true
                        ) {
                            resourcesProvider.getString(R.string.end_date_error)
                        } else null
                    )
                )
            } else {
                it.copy(
                    datePickerState = it.datePickerState.copy(isShowClock = false),
                    startTimeField = it.startTimeField.copy(
                        error = if (end.toLocalDateTime(DEFAULT_DATE_TIME_PATTERN)
                                ?.isBefore(res) == true
                        ) {
                            resourcesProvider.getString(R.string.start_date_error)
                        } else null
                    ),
                    endTimeField = it.endTimeField.copy(
                        value = resString,
                        error = if (start.toLocalDateTime(DEFAULT_DATE_TIME_PATTERN)
                                ?.isAfter(res) == true
                        ) {
                            resourcesProvider.getString(R.string.end_date_error)
                        } else null
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

    fun resetBottomSheetState() {
        _viewState.update {
            it.copy(
                bottomSheetState = CreateEditEventViewState.BottomSheetState.None
            )
        }
    }
}

internal data class CreateEditEventViewState(
    val state: State = State.Default,
    val nameField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val placeField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val startTimeField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val endTimeField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val maxParticipantField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val descriptionField: CreateEditFieldPresentationModel = CreateEditFieldPresentationModel(),
    val error: UIErrorType = UIErrorType.None,
    val datePickerState: DatePickerState = DatePickerState(),
    val bottomSheetState: BottomSheetState = BottomSheetState.None,
) {

    fun showBottomSheet(): Boolean = bottomSheetState != BottomSheetState.None

    sealed interface State {
        object Default : State
        object Loading : State
        object Error : State
        object Success : State
    }

    data class DatePickerState(
        val isShowCalendar: Boolean = false,
        val isShowClock: Boolean = false,
        val isStartDateChose: Boolean = true,
        val initialDateTime: LocalDateTime? = null,
        val cacheDateTime: LocalDateTime? = null
    )

    sealed class BottomSheetState {
        object None : BottomSheetState()
        data class SearchState(
            val searchKey: String = "",
            val options: List<Pair<String, String>> = listOf(),
            val isError: Boolean = false
        ) : BottomSheetState()
    }
}

internal data class CreateEditFieldPresentationModel(
    val name: String = "",
    val hint: String = "",
    val value: String = "",
    val type: FieldType = FieldType.NAME,
    val error: String? = null
) {
    enum class FieldType {
        NAME, START_TIME, END_TIME, DESTINATION, MAX_PARTICIPANT, DESCRIPTION
    }
}