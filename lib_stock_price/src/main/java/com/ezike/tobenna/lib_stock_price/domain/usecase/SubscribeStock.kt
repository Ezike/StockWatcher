package com.ezike.tobenna.lib_stock_price.domain.usecase

import com.ezike.tobenna.lib_stock_price.AsyncDispatcher
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class SubscribeStock @Inject constructor(
    private val stockRepository: StockRepository,
    private val dispatcher: AsyncDispatcher
) {

    public suspend fun execute() {
        withContext(dispatcher.io) {
            stockRepository.subscribeAll()
        }
    }
}
