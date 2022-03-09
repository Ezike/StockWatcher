package com.ezike.tobenna.lib_stock_price.data.cache

import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private object MapStore : Store {

    private val mutex = Mutex()
    private val cache = Stock.values().associate { security ->
        security.isin to CacheModel(
            name = security.name,
            id = security.isin,
            price = null,
            openPrice = null
        )
    }.toMutableMap()

    override suspend fun <T> getData(
        query: (Map<String, CacheModel>) -> T
    ): T = atomic { query(it.toMap()) }

    override suspend fun save(id: String, saver: (CacheModel) -> CacheModel) {
        atomic atm@{ map ->
            val cacheModel = map[id] ?: return@atm
            val result = saver(cacheModel)
            map[result.id] = result
        }
    }

    private suspend inline fun <T> atomic(
        action: (MutableMap<String, CacheModel>) -> T
    ): T = mutex.withLock { action(cache) }
}

internal interface Store {
    suspend fun <T> getData(query: (Map<String, CacheModel>) -> T): T
    suspend fun save(id: String, saver: (CacheModel) -> CacheModel)

    companion object
}

internal val Store.Companion.Map: Store
    get() = MapStore
