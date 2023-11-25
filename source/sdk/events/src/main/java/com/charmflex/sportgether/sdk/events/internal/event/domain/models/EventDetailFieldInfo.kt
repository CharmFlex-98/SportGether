package com.charmflex.sportgether.sdk.events.internal.event.domain.models

data class EventDetailFieldInfo(
    val name: String = "",
    val hint: String = "",
    val value: String = "",
    val type: FieldType = FieldType.NAME
) {
    enum class FieldType {
        NAME, START_TIME, END_TIME, DESTINATION, MAX_PARTICIPANT, DESCRIPTION
    }
}