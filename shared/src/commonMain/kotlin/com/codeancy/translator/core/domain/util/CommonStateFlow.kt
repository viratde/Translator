package com.codeancy.translator.core.domain.util

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

expect class CommonStateFlow<T>(
    flow: StateFlow<T>
) : StateFlow<T> {

    override val replayCache: List<T>

    override suspend fun collect(collector: FlowCollector<T>): Nothing

    override val value: T

}


fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)