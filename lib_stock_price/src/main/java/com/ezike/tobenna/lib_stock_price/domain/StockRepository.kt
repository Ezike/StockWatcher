package com.ezike.tobenna.lib_stock_price.domain

import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import kotlinx.coroutines.flow.Flow

public interface StockRepository {
    public val response: Flow<StockResponse>
    public suspend fun subscribe(id: String)
    public suspend fun unSubscribe(id: String)
    public suspend fun subscribeAll()
    public suspend fun unsubscribeAll()
}
