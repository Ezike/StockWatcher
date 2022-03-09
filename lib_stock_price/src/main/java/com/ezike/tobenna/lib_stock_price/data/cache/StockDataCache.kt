package com.ezike.tobenna.lib_stock_price.data.cache

import com.ezike.tobenna.lib_stock_price.domain.model.StockData

internal interface StockDataCache {
    suspend fun data(): List<StockData>
    suspend fun findById(id: String): CacheModel?
    suspend fun insert(cacheModel: CacheModel)
}
