package com.ezike.tobenna.remote.service

import com.ezike.tobenna.remote.mapper.WebSocketEventMapper
import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.ezike.tobenna.remote.model.WebSocketEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class StockRemoteImpl @Inject constructor(
    private val scarletStockService: ScarletStockService,
    private val eventMapper: WebSocketEventMapper
) : StockRemote {

    override val events: Flow<WebSocketEvent>
        get() = scarletStockService.events.map(eventMapper::toSocketEvent)

    override val stockInfo: Flow<StockRemoteModel>
        get() = scarletStockService.stockInfo

    override fun subscribe(data: SubscribeData) {
        scarletStockService.subscribe(data)
    }

    override fun unSubscribe(data: UnsubscribeData) {
        scarletStockService.unSubscribe(data)
    }
}
