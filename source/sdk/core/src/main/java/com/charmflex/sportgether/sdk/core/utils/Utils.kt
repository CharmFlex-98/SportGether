package com.charmflex.sportgether.sdk.core.utils

import kotlinx.coroutines.CancellationException

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