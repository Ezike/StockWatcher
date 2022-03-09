package com.ezike.tobenna.lib_stock_price

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

private class BaseTestAsyncDispatcher(
    private val coroutineContext: CoroutineContext
) : AsyncDispatcher {
    override val main: CoroutineDispatcher get() = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
    override val io: CoroutineDispatcher get() = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
    override val default: CoroutineDispatcher get() = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
}

internal fun StandardTestAsyncDispatcher(): AsyncDispatcher =
    BaseTestAsyncDispatcher(coroutineContext = StandardTestDispatcher())

internal fun TestScope.UnconfinedTestAsyncDispatcher(): AsyncDispatcher =
    BaseTestAsyncDispatcher(UnconfinedTestDispatcher(testScheduler))
