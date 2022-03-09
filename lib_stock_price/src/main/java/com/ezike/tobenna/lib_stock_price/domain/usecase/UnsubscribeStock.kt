package com.ezike.tobenna.lib_stock_price.domain.usecase

import com.ezike.tobenna.lib_stock_price.AsyncDispatcher
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class UnsubscribeStock @Inject constructor(
    private val stockRepository: StockRepository,
    private val dispatcher: AsyncDispatcher,
    private val appCoroutineScope: CoroutineScope
) {

    public fun execute() {
        appCoroutineScope.launch {
            withContext(dispatcher.io) {
                stockRepository.unsubscribeAll()
            }
        }
    }
}
