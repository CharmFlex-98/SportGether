package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.internal.event.domain.mapper.EventDetailPresentationModelMapper
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetParticipantsUseCase
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.JoinEventUseCase
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.QuitEventUseCase
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDetailsViewModel(
    private val joinEventUseCase: JoinEventUseCase,
    private val quitEventUseCase: QuitEventUseCase,
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val mapper: EventDetailPresentationModelMapper,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val eventId: Int,
    private val shouldRefresh: Boolean,
    private val routeNavigator: RouteNavigator,
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventDetailsViewState())
    val viewState = _viewState.asStateFlow()

    private val _participantViewState = MutableStateFlow(listOf<ParticipantsData>())
    val participantViewState = _participantViewState.asStateFlow()

    class Factory @Inject constructor(
        private val getEventDetailsUseCase: GetEventDetailsUseCase,
        private val quitEventUseCase: QuitEventUseCase,
        private val mapper: EventDetailPresentationModelMapper,
        private val getParticipantsUseCase: GetParticipantsUseCase,
        private val joinEventUseCase: JoinEventUseCase,
        private val routeNavigator: RouteNavigator,
    ) {
        fun create(eventId: Int?, shouldRefresh: Boolean): EventDetailsViewModel {
            val id = checkNotNull(eventId)

            return EventDetailsViewModel(
                joinEventUseCase,
                quitEventUseCase,
                getEventDetailsUseCase,
                mapper,
                getParticipantsUseCase,
                id,
                shouldRefresh,
                routeNavigator,
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
            getEventDetailsUseCase(eventId = eventId, refresh = shouldRefresh).fold(
                onSuccess = {
                    _viewState.update { state ->
                        state.copy(
                            isHost = it.isHost,
                            isJoined = it.isJoined,
                            fields = mapper.map(it)
                        )
                    }
                },
                onFailure = {
                    // TODO:
                }
            )
        }
    }

    fun onCheckParticipants() {
        viewModelScope.launch {
            toggleLoading(true)
            getParticipantsUseCase(eventId).fold(
                onSuccess = {
                    _participantViewState.value = it
                    _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            bottomSheetState = EventDetailsViewState.BottomSheetState.ShowParticipantDetailState
                        )
                    }
                },
                onFailure = {
                    // More to come
                    toggleLoading(false)
                }
            )
        }
    }

    private fun toggleLoading(loading: Boolean) {
        _viewState.update {
            it.copy(
                isLoading = loading
            )
        }
    }

    fun resetBottomSheetState() {
        _viewState.update {
            it.copy(
                bottomSheetState = EventDetailsViewState.BottomSheetState.None
            )
        }
    }

    fun onPrimaryAction() {
        if (_viewState.value.isHost) editEvent()
        else if (_viewState.value.isJoined) {
            _viewState.update {
                it.copy(
                    bottomSheetState = EventDetailsViewState.BottomSheetState.QuitEventConfirmationState
                )
            }
        } else joinEvent()
    }

    fun onSecondaryAction() {
        if (_viewState.value.isHost) {
            _viewState.update {
                it.copy(
                    bottomSheetState = EventDetailsViewState.BottomSheetState.CancelEventConfirmationState
                )
            }
        }
    }

    private fun editEvent() {
        routeNavigator.navigateTo(EventRoutes.editEventDestination(eventId = eventId))
    }

    fun onCancelEvent() {
        // Cancel Event logic
    }

    fun onQuitEvent() {
        viewModelScope.launch {
            quitEventUseCase.invoke(eventId = eventId).fold(
                onSuccess = {
                    _viewState.update {
                        it.copy(
                            quitSuccess = true
                        )
                    }
                },
                onFailure = {
                    // todo
                }
            )
        }
    }

    private fun joinEvent() {
        viewModelScope.launch {
            joinEventUseCase.invoke(eventId = eventId).fold(
                onSuccess = {
                    _viewState.update {
                        it.copy(
                            joinSuccess = true
                        )
                    }
                },
                onFailure = {
                    _viewState.update {
                        it.copy(
                            errorType = UIErrorType.GenericError
                        )
                    }
                }
            )
        }
    }

    fun onBack(needRefresh: Boolean) {
        if (needRefresh) {
            val data = mapOf(
                EventRoutes.Args.SHOULD_REFRESH to true,
                EventRoutes.Args.SHOULD_REFRESH_SCHEDULED to true
            )
            routeNavigator.popWithArguments(data)
        } else {
            routeNavigator.pop()
        }
    }
}

internal data class EventDetailsViewState(
    val fields: List<EventInfoPresentationModel> = listOf(),
    val isHost: Boolean = false,
    val isJoined: Boolean = false,
    val isLoading: Boolean = false,
    val joinSuccess: Boolean = false,
    val quitSuccess: Boolean = false,
    val errorType: UIErrorType = UIErrorType.None,
    val bottomSheetState: BottomSheetState = BottomSheetState.None
) {
    sealed class BottomSheetState {
        object ShowParticipantDetailState : BottomSheetState()
        object CancelEventConfirmationState : BottomSheetState()
        object QuitEventConfirmationState : BottomSheetState()
        object None : BottomSheetState()
        object Error : BottomSheetState()
    }

    fun showBottomSheet(): Boolean = bottomSheetState != BottomSheetState.None
}

internal sealed interface EventInfoPresentationModel

internal data class EventDetailBasicPresentationModel(
    val name: String,
    val value: String
) : EventInfoPresentationModel

internal data class EventParticipantDetailPresentationModel(
    val name: String,
    val joinedCount: Int,
    val icons: List<Drawable>,
    val maxAvailableCount: Int
) : EventInfoPresentationModel

internal class ParticipantsData(
    val id: Int,
    val name: String,
    val icon: Drawable?
)

