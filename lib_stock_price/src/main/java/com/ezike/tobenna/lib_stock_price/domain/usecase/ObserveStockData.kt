package com.ezike.tobenna.lib_stock_price.domain.usecase

import com.ezike.tobenna.lib_stock_price.AsyncDispatcher
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.lib_stock_price.handler.EventHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

public class ObserveStockData @Inject constructor(
    private val stockRepository: StockRepository,
    private val dispatcher: AsyncDispatcher,
    private val eventHandler: EventHandler,
    private val coroutineScope: CoroutineScope
) {

    private val response: SharedFlow<StockResponse>
        get() = stockRepository.response
            .conflate()
            .flowOn(dispatcher.io)
            .shareIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = SHARE_STOP_MILLIS)
            )

    public val stockData: Flow<List<StockData>>
        get() = response.filterIsInstance<StockResponse.Data>().map { it.stockList }

    public val connectionState: Flow<EventData>
        get() = eventHandler.handle(
            data = response,
            coroutineScope = coroutineScope
        )

    private companion object {
        const val SHARE_STOP_MILLIS: Long = 3000
    }
}
