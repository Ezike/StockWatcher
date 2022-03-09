package com.ezike.tobenna.lib_stock_price.data.di

import com.ezike.tobenna.lib_stock_price.data.StockRepositoryImpl
import com.ezike.tobenna.lib_stock_price.data.cache.InMemoryCache
import com.ezike.tobenna.lib_stock_price.data.cache.Map
import com.ezike.tobenna.lib_stock_price.data.cache.StockDataCache
import com.ezike.tobenna.lib_stock_price.data.cache.Store
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
internal interface DataModule {

    @get:Binds
    val StockRepositoryImpl.stockRepository: StockRepository

    @get:[Binds Singleton]
    val InMemoryCache.cache: StockDataCache

    companion object {
        @[Provides Singleton]
        fun store(): Store = Store.Map
    }
}
