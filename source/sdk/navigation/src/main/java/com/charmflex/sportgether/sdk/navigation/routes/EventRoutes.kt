package com.charmflex.sportgether.sdk.navigation.routes

object EventRoutes {
    const val ROOT = "/event"

    object Args {
        const val EVENT_ID = "event_id"
        const val IS_EDIT_EVENT = "is_edit_event"
    }

    val eventDetailsRoute = buildRoute("$ROOT/event_details") {
        addArg(Args.EVENT_ID)
    }

    val createEditEventRoute = buildRoute("$ROOT/event_create_edit") {
        addArg(Args.EVENT_ID)
    }

    fun eventDetailsDestination(eventId: Int): String {
        return buildDestination(eventDetailsRoute) {
            withArg(Args.EVENT_ID, eventId.toString())
        }
    }

    fun createEventScreen(): String {
        return buildDestination(createEditEventRoute) {
            withArg(Args.IS_EDIT_EVENT, false.toString())
        }
    }

    fun editEventDestination(eventId: Int): String {
        return buildDestination(createEditEventRoute) {
            withArg(Args.EVENT_ID, eventId.toString())
        }
    }
}