package com.charmflex.sportgether.sdk.core.utils

interface Mapper<T, U> {
    fun map(from: T): U
}