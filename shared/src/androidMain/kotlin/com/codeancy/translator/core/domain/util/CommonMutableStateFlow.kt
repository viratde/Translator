package com.codeancy.translator.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual open class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T> by flow {


    actual override var value: T
        get() = flow.value
        set(value) {
            flow.value = value
        }

    @ExperimentalCoroutinesApi
    actual override fun resetReplayCache() {
        flow.resetReplayCache()
    }

    actual override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    actual override fun tryEmit(value: T): Boolean {
        return flow.tryEmit(value)
    }

    actual override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }

    actual override val replayCache: List<T>
        get() = flow.replayCache

    actual override suspend fun emit(value: T) {
        flow.emit(value)
    }

    actual override fun compareAndSet(expect: T, update: T): Boolean {
        return flow.compareAndSet(expect, update)
    }

}