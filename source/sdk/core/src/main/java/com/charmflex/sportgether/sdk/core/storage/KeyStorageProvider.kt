package com.charmflex.sportgether.sdk.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

private const val SPORT_GETHER_FILE_PATH = "SPORT-GETHER-SHARED-PREFERENCES-PATH"

interface KeyStorageProvider {
    fun setString(key: String, value: String)
    fun getString(key: String, default: String = ""): String

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, default: Boolean): Boolean

    fun clearAllData()

    companion object {

        fun create(context: Context): KeyStorageProvider {
            return KeyStorageProviderImpl(context)
        }


    }
}


class KeyStorageProviderImpl @Inject constructor(
    private val context: Context
) : KeyStorageProvider {

    private lateinit var sharedPrefs: SharedPreferences

    init {
        initSharedPreferences()
    }

    private fun initSharedPreferences() {
        sharedPrefs = EncryptedSharedPreferences.create(
            SPORT_GETHER_FILE_PATH,
            generateMainKeyAlias(),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun generateMainKeyAlias(): String {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        return MasterKeys.getOrCreate(keyGenParameterSpec)
    }

    override fun setString(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    override fun getString(key: String, default: String): String {
        return sharedPrefs.getString(key, default) ?: default
    }

    override fun setBoolean(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, default)
    }

    override fun clearAllData() {
        sharedPrefs.edit().clear().apply()
    }

}