package com.charmflex.sportgether.app.home

import com.charmflex.sportgether.sdk.events.EventService
import javax.inject.Inject

class ServiceWrapper @Inject constructor(
    val service: EventService
) {
}