package com.charmflex.sportgether.sdk.core.utils

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import javax.inject.Inject

class ResourcesProvider @Inject constructor(
    private val appContext: Context
) {
    fun getString(@StringRes stringId: Int): String {
        return (appContext as? Application)?.getString(stringId) ?: ""
    }

    fun getDrawable(@DrawableRes drawableId: Int): Drawable? {
        return AppCompatResources.getDrawable(appContext, drawableId)
    }
}