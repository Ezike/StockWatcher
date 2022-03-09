package com.ezike.tobenna.lib_stock_price.handler

import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class EventHandler @Inject constructor(
    private val ticker: FlowBasedTimeTicker
) {

    internal fun handle(
        data: Flow<StockResponse>,
        coroutineScope: CoroutineScope
    ): Flow<EventData> =
        data.distinctUntilChangedBy { stockResponse ->
            stockResponse::class.simpleName
        }.flatMapLatest flm@{ stockResponse ->
            when (stockResponse) {
                is StockResponse.Inactive -> ticker.start(coroutineScope)
                is StockResponse.Data -> ticker.stop()
            }
            return@flm ticker
                .elapsedTime
                .map { time -> eventData(time, stockResponse) }
        }

    private fun eventData(
        time: Long,
        response: StockResponse
    ) = EventData(
        time = time,
        isError = response is StockResponse.Error
    )
}
