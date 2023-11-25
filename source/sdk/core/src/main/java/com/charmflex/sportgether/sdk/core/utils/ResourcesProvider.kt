package com.charmflex.sportgether.sdk.core.utils

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(
    private val appContext: Context
) {
    fun getString(@StringRes stringId: Int): String {
        return (appContext as? Application)?.getString(stringId) ?: ""
    }
}