package com.charmflex.sportgether.sdk.events.internal.place

import android.content.Context
import android.util.Log
import com.charmflex.sportgether.sdk.app_config.AppConfig
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import javax.inject.Inject
import javax.inject.Singleton

interface PlaceClientManager {
    fun initialise()
    fun execute()
}

@Singleton
internal class GPlaceClientManagerImpl @Inject constructor(
    private val context: Context,
    private val appConfig: AppConfig
) : PlaceClientManager {
    private lateinit var _client: PlacesClient
    override fun initialise() {
        Log.d("PLACE_CLIENT::", "Initialising Start")
        Places.initializeWithNewPlacesApiEnabled(context, appConfig.placeAPIKey)
        _client =  Places.createClient(context)
        Log.d("PLACE_CLIENT::", "Done Initialising")
    }

    override fun execute() {
        // todo: I don't know, see how
    }
}