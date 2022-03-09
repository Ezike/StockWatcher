package com.ezike.tobenna.lib_stock_price.handler

import com.ezike.tobenna.lib_stock_price.TimeSource
import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

public class StockEventHandler @Inject constructor(
    private val ticker: ChannelBasedTicker,
    private val timeSource: TimeSource
) {

    private val tickerChannel = Channel<ChannelBasedTicker.TickerEvent>()

    internal fun handle(
        coroutineScope: CoroutineScope,
        data: Flow<StockResponse>
    ): Flow<EventData> {
        coroutineScope.launch {
            data.distinctUntilChangedBy { stockResponse ->
                stockResponse::class.simpleName
            }.collect { stockResponse ->
                when (stockResponse) {
                    is StockResponse.Inactive -> {
                        val start = ChannelBasedTicker.Start(
                            event = stockResponse,
                            startTime = timeSource.time
                        ); tickerChannel.send(start)
                    }
                    is StockResponse.Data -> tickerChannel.send(ChannelBasedTicker.Stop)
                }
            }
        }

        return ticker
            .start(coroutineScope, tickerChannel)
            .receiveAsFlow()
            .map { (time, response) -> eventData(time, response) }
    }

    private fun eventData(
        time: Long,
        response: StockResponse
    ) = EventData(
        time = time,
        isError = response is StockResponse.Error
    )
}
