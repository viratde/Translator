package com.codeancy.translator.core.domain.util

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

actual class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : StateFlow<T> {

    actual override val value: T
        get() = flow.value

    actual override val replayCache: List<T>
        get() = flow.replayCache


    actual override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }

}