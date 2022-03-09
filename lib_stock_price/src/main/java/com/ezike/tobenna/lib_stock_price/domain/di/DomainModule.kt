package com.ezike.tobenna.lib_stock_price.domain.di

import com.ezike.tobenna.lib_stock_price.AsyncDispatcher
import com.ezike.tobenna.lib_stock_price.DefaultAsyncDispatcher
import com.ezike.tobenna.lib_stock_price.TimeSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
internal interface DomainModule {

    @get:[Binds Singleton]
    val DefaultAsyncDispatcher.dispatcher: AsyncDispatcher

    companion object {
        @Provides
        fun timeProvider(): TimeSource = TimeSource
    }
}
