package com.charmflex.sportgether.sdk.core.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

fun countDownTimer(totalSeconds: Int): Flow<Int> {
    return ((totalSeconds - 1).downTo(0)).asFlow()
        .onEach {
            println("up stream: $it")
            delay(1000) }
        .conflate()
        .onStart { emit(totalSeconds) }
}

//fun main() {
//    runBlocking {
//        countDownTimer(60).collect {
//            println("collecting $it")
//            println("done collecting $it")
//        }
//    }
//}