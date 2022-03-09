package com.ezike.tobenna.lib_stock_price.data.cache

import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import javax.inject.Inject

internal class InMemoryCache @Inject constructor(
    private val mapper: CacheModelMapper,
    private val store: Store
) : StockDataCache, Store by store {

    override suspend fun data(): List<StockData> =
        getData { it.values.map(mapper::toStockData) }

    override suspend fun findById(
        id: String
    ): CacheModel? = getData { it[id] }

    override suspend fun insert(cacheModel: CacheModel) {
        save(
            id = cacheModel.id,
            saver = fun(model: CacheModel) = model.copy(
                price = cacheModel.price,
                openPrice = cacheModel.openPrice
            )
        )
    }
}
