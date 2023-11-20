package com.charmflex.sportgether.sdk.core.utils

open class SingletonHolder<T>(
    val init: () -> T
) {
    @Volatile
    private var _instance: T? = null

    fun getInstance(): T {
        return _instance ?: init().also { _instance = it }
    }
}