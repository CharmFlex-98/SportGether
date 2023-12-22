package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.GetEventDetailsUseCase
import com.charmflex.sportgether.sdk.events.internal.event.domain.usecases.JoinEventUseCase
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDetailsViewModel(
    private val joinEventUseCase: JoinEventUseCase,
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val eventId: Int,
    private val routeNavigator: RouteNavigator
) : ViewModel() {
    private val _viewState = MutableStateFlow(EventDetailsViewState())
    val viewState = _viewState.asStateFlow()

    class Factory @Inject constructor(
        private val getEventDetailsUseCase: GetEventDetailsUseCase,
        private val joinEventUseCase: JoinEventUseCase,
        private val routeNavigator: RouteNavigator
    ) {
        fun create(eventId: Int?): EventDetailsViewModel {
            val id = checkNotNull(eventId)

            return EventDetailsViewModel(
                joinEventUseCase,
                getEventDetailsUseCase,
                id,
                routeNavigator
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
            getEventDetailsUseCase(eventId = eventId).fold(
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

    fun onCheckParticipants() {
        _viewState.update {
            it.copy(
                bottomSheetState = EventDetailsViewState.BottomSheetState.ShowParticipantDetailState
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

    fun onPrimaryButtonClick() {
        // TODO: Condition need to check whether the event is host under this user
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

    fun onBack() {
        val data = mapOf(EventRoutes.Args.SHOULD_REFRESH to true)
        routeNavigator.popWithArguments(data)
    }
}

sealed interface EventDetailContentInfo

internal data class EventDetailsViewState(
    val fields: List<EventDetailContentInfo> = listOf(),
    val isLoading: Boolean = false,
    val joinSuccess: Boolean = false,
    val errorType: UIErrorType = UIErrorType.None,
    val bottomSheetState: BottomSheetState = BottomSheetState.None
) {
    sealed class BottomSheetState {
        object ShowParticipantDetailState : BottomSheetState()
        object None : BottomSheetState()
        object Error : BottomSheetState()
    }

    fun showBottomSheet(): Boolean = bottomSheetState != BottomSheetState.None
}

internal data class EventDetailTwoLineInfo(
    val name: String,
    val value: String
) : EventDetailContentInfo

internal data class EventDetailParticipantsInfo(
    val name: String,
    val icons: List<Drawable>
) : EventDetailContentInfo

