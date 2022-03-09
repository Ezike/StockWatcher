package com.ezike.tobenna.lib_stock_price.data.cache

import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import javax.inject.Inject

internal class CacheModelMapper @Inject constructor() {

    fun toStockData(
        model: CacheModel
    ): StockData = StockData(
        name = model.name,
        id = model.id,
        price = model.price,
        openPrice = model.openPrice
    )
}
