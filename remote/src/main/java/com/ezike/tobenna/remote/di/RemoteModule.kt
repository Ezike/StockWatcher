package com.ezike.tobenna.remote.di

import android.app.Application
import com.ezike.tobenna.remote.service.ScarletStockService
import com.ezike.tobenna.remote.service.StockRemote
import com.ezike.tobenna.remote.service.StockRemoteImpl
import com.ezike.tobenna.remote.util.ScarletStockServiceProvider
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteModule {

    @get:Binds
    val StockRemoteImpl.stockService: StockRemote

    companion object {
        @[Provides Singleton]
        fun provideScarlet(
            application: Application,
            moshi: Moshi
        ): ScarletStockService = ScarletStockServiceProvider(
            application = application,
            moshi = moshi
        ).create()

        @[Provides Singleton]
        fun moshi(): Moshi = Moshi.Builder().build()
    }
}
