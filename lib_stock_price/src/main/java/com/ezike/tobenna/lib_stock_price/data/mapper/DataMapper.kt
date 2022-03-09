package com.ezike.tobenna.lib_stock_price.data.mapper

import com.ezike.tobenna.lib_stock_price.data.cache.CacheModel
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.WebSocketEvent
import javax.inject.Inject

internal class DataMapper @Inject constructor() {

    fun toStockResponse(
        data: List<StockData>,
        event: WebSocketEvent
    ): StockResponse = when (event) {
        is WebSocketEvent.MessageReceived,
        WebSocketEvent.ConnectionOpened -> StockResponse.Data(
            stockList = data
        )
        is WebSocketEvent.ConnectionClosed -> StockResponse.Closed(
            stockList = data,
            message = event.cause
        )
        is WebSocketEvent.ConnectionClosing -> StockResponse.Closed(
            stockList = data,
            message = event.cause
        )
        is WebSocketEvent.ConnectionFailed -> StockResponse.Error(
            stockList = data,
            message = event.error.message
        )
    }

    fun toCacheModel(remoteModel: StockRemoteModel): CacheModel =
        CacheModel(
            name = "",
            id = remoteModel.isin,
            openPrice = remoteModel.openPrice,
            price = remoteModel.price
        )
}
