package com.charmflex.sportgether.sdk.core.utils

open class SingletonHolder<in Arg, out T>(
    val factory: (Arg) -> T
) {
    @Volatile
    private var _instance: T? = null

    fun getInstance(arg: Arg): T {
        return _instance ?: factory(arg).also { _instance = it }
    }
}