package com.ezike.tobenna.lib_stock_price.handler

import com.ezike.tobenna.lib_stock_price.TimeSource
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.selects.select
import javax.inject.Inject

public class ChannelBasedTicker @Inject constructor(
    private val timeSource: TimeSource
) {

    internal fun start(
        scope: CoroutineScope,
        eventChannel: ReceiveChannel<TickerEvent>
    ): ReceiveChannel<Pair<Long, StockResponse>> = scope.produce {
        var event: TickerEvent = Idle
        while (isActive) {
            select<Unit> {
                eventChannel.onReceive { event = it }
                (event as? Start)?.let { (start, event) ->
                    onSend(
                        param = (timeSource.time - start) to event,
                        block = { delay(3000) }
                    )
                }
            }
        }
    }

    internal interface TickerEvent
    internal data class Start(
        val startTime: Long,
        val event: StockResponse
    ) : TickerEvent

    internal object Stop : TickerEvent
    internal object Idle : TickerEvent
}
