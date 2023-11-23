package com.charmflex.sportgether.sdk.core.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface RouteNavigator {

    val navigationEvent: Flow<NavigationEvent>

    fun navigateTo(route: String)

    fun pop()

}

class RouteNavigatorImpl @Inject constructor() : RouteNavigator {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    override val navigationEvent: Flow<NavigationEvent>
        get() = _navigationEvent.asSharedFlow()

    override fun navigateTo(route: String) {
        _navigationEvent.tryEmit(NavigateTo(route))
    }

    override fun pop() {
        _navigationEvent.tryEmit(Pop)
    }

}

sealed interface NavigationEvent

data class NavigateTo(val route: String) : NavigationEvent
object Pop : NavigationEvent