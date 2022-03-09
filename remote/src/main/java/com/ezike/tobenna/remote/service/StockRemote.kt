package com.ezike.tobenna.remote.service

import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.ezike.tobenna.remote.model.WebSocketEvent
import kotlinx.coroutines.flow.Flow

public interface StockRemote {
    public val events: Flow<WebSocketEvent>
    public val stockInfo: Flow<StockRemoteModel>
    public fun subscribe(data: SubscribeData)
    public fun unSubscribe(data: UnsubscribeData)
}
