package com.ezike.tobenna.lib_stock_price.data.cache

import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import javax.inject.Inject

internal class InMemoryCache @Inject constructor(
    private val mapper: CacheModelMapper,
    private val store: Store
) : StockDataCache {

    override suspend fun data(): List<StockData> =
        store.getData { data -> data.values.map(mapper::toStockData) }

    override suspend fun findById(
        id: String
    ): CacheModel? = store.getData { map -> map[id] }

    override suspend fun insert(cacheModel: CacheModel) {
        store.save(
            id = cacheModel.id,
            saver = fun(model: CacheModel) = model.copy(
                price = cacheModel.price,
                openPrice = cacheModel.openPrice
            )
        )
    }
}
