package com.ezike.tobenna.lib_stock_price.domain.usecase

import app.cash.turbine.test
import com.ezike.tobenna.lib_stock_price.AsyncDispatcher
import com.ezike.tobenna.lib_stock_price.StandardTestAsyncDispatcher
import com.ezike.tobenna.lib_stock_price.UnconfinedTestAsyncDispatcher
import com.ezike.tobenna.lib_stock_price.domain.StockRepository
import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.lib_stock_price.handler.EventHandler
import com.ezike.tobenna.lib_stock_price.stockData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class ObserveStockDataTest {

    @Test
    fun `when response is requested then the correct data is returned`() = runTest {
        // Given
        val stockRepository = mock<StockRepository> {
            on { response } doReturn flowOf(
                StockResponse.Error(
                    stockList = stockData(),
                    message = "No stonks"
                ),
                StockResponse.Data(
                    stockList = stockData()
                )
            )
        }

        val observeStockData = createObserveStockData(
            stockRepository = stockRepository,
            dispatcher = UnconfinedTestAsyncDispatcher()
        )

        // when
        val response = mutableListOf<StockData>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            observeStockData.stockData.collect { response += it }
        }
        job.cancel()

        // then
        assertEquals(stockData(), response)
    }

    @Test
    fun `when data state is requested then the correct data is returned`() = runTest {
        // Given
        val stockRepository = mock<StockRepository> {
            on { response } doReturn flowOf(
                StockResponse.Error(
                    stockList = stockData(),
                    message = "No stonks"
                ),
                StockResponse.Data(
                    stockList = stockData()
                )
            )
        }

        val eventHandler = mock<EventHandler> {
            on { handle(any(), any()) } doReturn flowOf(
                EventData(time = 2L, isError = true),
                EventData(time = 2L, isError = false)
            )
        }

        val observeStockData = createObserveStockData(
            stockRepository = stockRepository,
            eventHandler = eventHandler,
            dispatcher = UnconfinedTestAsyncDispatcher()
        )

        // when
        val response = observeStockData.connectionState

        // then
        response.test {
            assertEquals(EventData(time = 2L, isError = true), awaitItem())
            assertEquals(EventData(time = 2L, isError = false), awaitItem())
            awaitComplete()
        }
    }

    private fun createObserveStockData(
        stockRepository: StockRepository = mock(),
        dispatcher: AsyncDispatcher = StandardTestAsyncDispatcher(),
        eventHandler: EventHandler = mock(),
        coroutineScope: CoroutineScope = TestScope()
    ) = ObserveStockData(
        stockRepository = stockRepository,
        dispatcher = dispatcher,
        eventHandler = eventHandler,
        coroutineScope = coroutineScope
    )
}
