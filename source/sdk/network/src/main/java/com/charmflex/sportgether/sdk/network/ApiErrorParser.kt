package com.charmflex.sportgether.sdk.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import javax.inject.Inject

interface ApiErrorParser {
    fun parse(httpException: HttpException): ApiExceptionBody
}


class ApiErrorParserImpl @Inject constructor(
    private val gson: Gson
) : ApiErrorParser {
    override fun parse(httpException: HttpException): ApiExceptionBody {
        return try {
            gson.fromJson(httpException.response()?.errorBody()?.string(), ApiExceptionBody::class.java)
        } catch (e: Exception) {
            Log.d(this.javaClass.simpleName, "ApiException parsing failed!")
            ApiExceptionBody(
                error = ApiExceptionBody.ErrorBody(
                    errorCode = 99999,
                    message = "ApiException parsing failed!"
                ),
            )
        }
    }
}

data class ApiExceptionBody(
    val error: ErrorBody
) {
    data class ErrorBody(
        @SerializedName("errorCode")
        val errorCode: Int,
        @SerializedName("message")
        val message: String
    )
}

interface ApiErrorMapper {
    fun map(apiExceptionBody: ApiExceptionBody): Exception
}