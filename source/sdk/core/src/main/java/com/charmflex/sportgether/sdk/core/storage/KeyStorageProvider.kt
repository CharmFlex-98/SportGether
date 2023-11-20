package com.charmflex.sportgether.sdk.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

private const val SPORT_GETHER_FILE_PATH = "SPORT-GETHER-SHARED-PREFERENCES-PATH"

interface KeyStorageProvider {
    fun setString(key: String, value: String)
    fun getString(key: String): String

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean

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
//        TODO("Not yet implemented")
    }

    override fun getString(key: String): String {
        TODO("Not yet implemented")
    }

    override fun setBoolean(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getBoolean(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearAllData() {
        TODO("Not yet implemented")
    }

}