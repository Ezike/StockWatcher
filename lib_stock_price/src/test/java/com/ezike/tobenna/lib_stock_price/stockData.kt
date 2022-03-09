package com.ezike.tobenna.lib_stock_price

import com.ezike.tobenna.lib_stock_price.data.cache.CacheModel
import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import com.ezike.tobenna.lib_stock_price.domain.model.StockData

internal fun stockData() = Stock.values().associate { security ->
    security.isin to StockData(
        name = security.name,
        price = 0.0,
        id = security.isin,
        openPrice = null
    )
}.values.toList()

internal fun TestCacheModel(id: String) = CacheModel(
    id = id,
    price = null,
    openPrice = null,
    name = ""
)