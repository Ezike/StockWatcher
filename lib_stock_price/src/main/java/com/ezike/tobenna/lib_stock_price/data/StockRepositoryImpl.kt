package com.ezike.tobenna.lib_stock_price.data

import com.ezike.tobenna.lib_stock_price.data.cache.StockDataCache
import com.ezike.tobenna.lib_stock_price.data.mapper.DataMapper
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.ezike.tobenna.remote.model.WebSocketEvent
import com.ezike.tobenna.remote.service.StockRemote
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class StockRepositoryImpl @Inject constructor(
    private val stockRemote: StockRemote,
    private val stockDataCache: StockDataCache,
    private val mapper: DataMapper
) : StockRepository {

    override val response: Flow<StockResponse>
        get() = combine(
            stockRemote.stockInfo.distinctUntilChanged(),
            stockRemote.events.distinctUntilChanged()
        ) { model, event ->
            stockDataCache.insert(cacheModel = mapper.toCacheModel(model))
            return@combine mapper.toStockResponse(
                data = stockDataCache.data(),
                event = event
            )
        }

    override suspend fun subscribe(id: String) {
        val isin = stockDataCache.findById(id)?.id ?: return
        val data = SubscribeData(isin = isin)
        stockRemote.subscribe(data)
    }

    override suspend fun unSubscribe(id: String) {
        val isin = stockDataCache.findById(id)?.id ?: return
        val data = UnsubscribeData(isin = isin)
        stockRemote.unSubscribe(data)
    }

    override suspend fun subscribeAll() {
        coroutineScope scope@{
            stockRemote.events
                .filterIsInstance<WebSocketEvent.ConnectionOpened>()
                .onEach {
                    stockDataCache.data().forEach {
                        subscribe(it.id)
                    }
                }.launchIn(this@scope)
        }
    }

    override suspend fun unsubscribeAll() {
        stockDataCache.data().forEach {
            unSubscribe(it.id)
        }
    }
}
