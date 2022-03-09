package com.ezike.tobenna.lib_stock_price.handler

import com.ezike.tobenna.lib_stock_price.TimeSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

public class FlowBasedTimeTicker @Inject constructor(
    private val timeSource: TimeSource
) {

    private var job: Job? = null
    private val elapsedTimeFlow = MutableSharedFlow<Long>()
    internal val elapsedTime: SharedFlow<Long>
        get() = elapsedTimeFlow.asSharedFlow()

    internal fun start(coroutineScope: CoroutineScope) {
        val errorTime = timeSource.time
        job = coroutineScope.launch {
            delay(INITIAL_DELAY_MILLIS)
            while (true) {
                elapsedTimeFlow.emit(timeSource.time - errorTime)
                delay(DELAY_MILLIS)
            }
        }
    }

    internal fun stop() {
        job?.cancel()
        job = null
    }

    private companion object {
        const val DELAY_MILLIS: Long = 3000
        const val INITIAL_DELAY_MILLIS: Long = 2000
    }
}
