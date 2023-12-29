package com.charmflex.sportgether.app.configs

import com.charmflex.sportgether.app.BuildConfig
import com.charmflex.sportgether.sdk.app_config.AppConfig

internal class DefaultAppConfig : AppConfig {
    override val baseUrl: String
        get() = BuildConfig.BASE_URL

    override val placeAPIKey: String
        get() = BuildConfig.PLACE_API_KEY
}