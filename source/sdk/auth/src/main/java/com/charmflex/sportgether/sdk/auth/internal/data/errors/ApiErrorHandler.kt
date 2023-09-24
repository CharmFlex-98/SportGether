package com.charmflex.sportgether.sdk.auth.internal.data.errors

import com.charmflex.sportgether.sdk.network.ApiErrorMapper
import com.charmflex.sportgether.sdk.network.ApiErrorParser
import retrofit2.HttpException
import javax.inject.Inject

internal class ApiErrorHandler @Inject constructor(
    private val parser: ApiErrorParser,
    private val apiErrorMapper: ApiErrorMapper
) {
    inline operator fun <T> invoke(action: () -> T): T {
        return try {
            action()
        } catch (e: Exception) {
            when (e) {
                is HttpException -> throw apiErrorMapper.map(parser.parse(e))
                else -> throw UnknownError()
            }
        }
    }
}