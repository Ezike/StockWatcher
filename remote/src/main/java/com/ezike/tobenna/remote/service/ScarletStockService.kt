package com.ezike.tobenna.remote.service

import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

internal interface ScarletStockService {

    @get:Receive
    val events: Flow<WebSocket.Event>

    @get:Receive
    val stockInfo: Flow<StockRemoteModel>

    @Send
    fun subscribe(data: SubscribeData)

    @Send
    fun unSubscribe(data: UnsubscribeData)
}
