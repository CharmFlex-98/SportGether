package com.charmflex.sportgether.sdk.core.utils

import kotlinx.coroutines.CancellationException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

inline fun <T> resultOf(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (cancellation: CancellationException) {
        // Rethrow for cancellation exception because coroutine need to catch this for proper cancellation
        throw cancellation
    } catch (e: Throwable) {
        Result.failure(exception = e)
    }
}

fun <T> unwrapResult(result: Result<T>): T {
    return result.getOrThrow()
}

const val DEFAULT_DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm a"
const val DATE_ONLY_DEFAULT_PATTERN = "dd-MM-yyyy"
const val TIME_ONLY_DEFAULT_PATTERN = "hh:mm a"


// TO CLIENT
fun String.toLocalDateTime(pattern: String): LocalDateTime? {
    if (this.isEmpty()) return null
    return LocalDateTime.parse(this, getDateTimeFormatter(pattern))
}

fun LocalDateTime?.toStringWithPattern(pattern: String): String {
    return this?.format(getDateTimeFormatter(pattern)) ?: ""
}

fun LocalDateTime?.toISO8601String(zoneId: ZoneId): String {
    return this?.atZone(zoneId)?.toInstant().toString()
}

fun String.fromISOToStringWithPattern(pattern: String): String{
    return fromISOToLocalDateTime().toStringWithPattern(pattern = pattern)
}

// INTERNAL

private fun getDateTimeFormatter(pattern: String): DateTimeFormatter {
    return DateTimeFormatter.ofPattern(pattern)
}

private fun String.fromISOToLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.parse(this), ZoneId.systemDefault())
}