package com.charmflex.sportgether.sdk.app_config
interface AppConfig {
    val baseUrl: String
    val placeAPIKey: String
}

interface AppConfigProvider {
    fun getAppConfig(): AppConfig
}