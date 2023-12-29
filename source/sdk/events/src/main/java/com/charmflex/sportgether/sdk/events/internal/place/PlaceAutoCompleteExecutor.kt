package com.charmflex.sportgether.sdk.events.internal.place

import android.content.Context
import android.graphics.Color
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.util.Log
import com.charmflex.sportgether.sdk.app_config.AppConfig
import com.charmflex.sportgether.sdk.app_config.AppConfigProvider
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.wait
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

interface PlaceAutoCompleteExecutor {
    fun onQuery(
        searchQuery: String,
        onFailure: (Exception) -> Unit,
        onSuccess: (List<Pair<String, String>>) -> Unit
    )

    fun onFetchPlaceDetails(
        placeId: String,
        onFailure: (Exception) -> Unit,
        onSuccess: (PlaceDetails) -> Unit
    )

    data class PlaceDetails(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )
}

@Singleton
internal class PlaceAutoCompleteExecutorImpl @Inject constructor(
    private val context: Context,
) : PlaceAutoCompleteExecutor {
    private lateinit var _client: PlacesClient
    private var _sessionToken: AutocompleteSessionToken? = null
        get() {
            return field ?: kotlin.run {
                field = AutocompleteSessionToken.newInstance()
                field
            }
        }

    init {
        initialise()
    }

    private fun initialise() {
        Log.d("PLACE_CLIENT::", "Initialising Start")
        (context as? AppConfigProvider)?.let {
            val appConfig = it.getAppConfig()
            Places.initializeWithNewPlacesApiEnabled(context, appConfig.placeAPIKey)
            _client = Places.createClient(context)
            Log.d("PLACE_CLIENT::", "Done Initialising")
            return
        }
        Log.d("PLACE_CLIENT::", "Error on Initialising")
    }

    override fun onQuery(
        searchQuery: String,
        onFailure: (Exception) -> Unit,
        onSuccess: (List<Pair<String, String>>) -> Unit
    ) {
        val request = FindAutocompletePredictionsRequest.builder().apply {
            Log.d("PLACE_CLIENT::", _sessionToken.toString())
            sessionToken = _sessionToken
            query = searchQuery
        }.build()

        _client.findAutocompletePredictions(request)
            .addOnSuccessListener {
                val list = it.autocompletePredictions.map { prediction ->
                    Pair(
                        prediction.placeId,
                        prediction.getFullText(BackgroundColorSpan(Color.WHITE)).toString()
                    )
                }
                onSuccess(list)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    override fun onFetchPlaceDetails(
        placeId: String,
        onFailure: (Exception) -> kotlin.Unit,
        onSuccess: (PlaceAutoCompleteExecutor.PlaceDetails) -> Unit
    ) {
        Log.d("PLACE_CLIENT::", "FETCHING PLACE DETAILS")
        val request =
            FetchPlaceRequest
                .builder(placeId, listOf(Place.Field.NAME, Place.Field.LAT_LNG))
                .setSessionToken(_sessionToken)
                .build()
        _client.fetchPlace(request)
            .addOnSuccessListener {
                val name = it.place.name
                val latLng = it.place.latLng
                if (name != null && latLng != null) {
                    Log.d("PLACE_CLIENT::", "FETCH SUCCESS")
                    Log.d("PLACE_CLIENT::", "name=$name lat=${latLng.latitude} long=${latLng.longitude}")
                    onSuccess(
                        PlaceAutoCompleteExecutor.PlaceDetails(
                            name = name,
                            latitude = latLng.latitude,
                            longitude = latLng.longitude
                        )
                    )
                } else {
                    Log.d("PLACE_CLIENT::", "FETCH RESULT ERROR")
                    onFailure(PlaceDetailsException("Cannot obtain place name or location"))
                }

            }
            .addOnFailureListener {
                Log.d("PLACE_CLIENT::", "FETCH FAILED")
                onFailure(PlaceDetailsException("Failed to fetch place details."))
            }

        _sessionToken = null
    }
}

internal data class PlaceDetailsException(
    val errorMessage: String
) : Exception()