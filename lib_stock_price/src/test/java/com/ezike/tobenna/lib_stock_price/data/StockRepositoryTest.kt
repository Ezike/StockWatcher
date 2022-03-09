package com.ezike.tobenna.lib_stock_price.data

import app.cash.turbine.test
import com.ezike.tobenna.lib_stock_price.TestCacheModel
import com.ezike.tobenna.lib_stock_price.data.cache.CacheModel
import com.ezike.tobenna.lib_stock_price.data.cache.StockDataCache
import com.ezike.tobenna.lib_stock_price.data.mapper.DataMapper
import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.lib_stock_price.stockData
import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.ezike.tobenna.remote.model.WebSocketEvent
import com.ezike.tobenna.remote.service.StockRemote
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnitParamsRunner::class)
internal class StockRepositoryTest {

    @Test
    fun `when response is requested then the correct data is returned`() = runTest {
        // Given
        val stockRemote = mock<StockRemote> {
            on {
                stockInfo
            } doReturn flowOf(
                StockRemoteModel(
                    isin = "US3429009",
                    price = 8993.22,
                    openPrice = 0.0
                )
            )
            on {
                events
            } doReturn flowOf(WebSocketEvent.ConnectionOpened)
        }

        val responseMapper = mock<DataMapper> {
            on {
                toStockResponse(
                    data = stockData(),
                    event = WebSocketEvent.ConnectionOpened
                )
            } doReturn StockResponse.Data(stockList = stockData())
        }

        val stockDataCache = mock<StockDataCache>().apply {
            whenever(data()) doReturn stockData()
        }

        val stockRepository = createStockRepository(
            stockRemote = stockRemote,
            responseMapper = responseMapper,
            stockDataCache = stockDataCache
        )

        // when
        val response = stockRepository.response

        // then
        response.test {
            assertEquals(
                StockResponse.Data(stockList = stockData()),
                awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    @Parameters(method = "paramForSubscribe")
    fun `when subscribe is called then the provided stock is subscribed to socket`(
        id: String,
        result: CacheModel?,
        count: Int
    ) = runTest {
        // Given
        val stockRemote = mock<StockRemote>()

        val stockDataCache = mock<StockDataCache>().apply {
            whenever(findById(id)) doReturn result
        }

        val stockRepository = createStockRepository(
            stockRemote = stockRemote,
            stockDataCache = stockDataCache
        )

        // when
        stockRepository.subscribe(Stock.TRIVAGO.isin)

        // then
        verify(stockRemote, times(count)).subscribe(
            SubscribeData(isin = id)
        )
    }

    fun paramForSubscribe() = arrayOf(
        arrayOf(
            Stock.TRIVAGO.isin,
            TestCacheModel(id = "US89686D1054"),
            1
        ),
        arrayOf(
            "random id",
            null,
            0
        )
    )

    @Test
    @Parameters(method = "paramForSubscribe")
    fun `when unsubscribe is called then the provided stock is unsubscribed from socket`(
        id: String,
        result: CacheModel?,
        count: Int
    ) = runTest {
        // Given
        val stockRemote = mock<StockRemote>()

        val stockDataCache = mock<StockDataCache>().apply {
            whenever(findById(id)) doReturn result
        }

        val stockRepository = createStockRepository(
            stockRemote = stockRemote,
            stockDataCache = stockDataCache
        )

        // when
        stockRepository.unSubscribe(id)

        // then
        verify(stockRemote, times(count)).unSubscribe(
            UnsubscribeData(isin = id)
        )
    }

    fun paramForSubscription() = arrayOf(
        arrayOf(
            Stock.MICROSOFT
        ),
        arrayOf(
            Stock.GOOGLE
        ),
        arrayOf(
            Stock.INVESCO
        ),
        arrayOf(
            Stock.ACCENTURE
        ),
        arrayOf(
            Stock.AMAZON
        ),
        arrayOf(
            Stock.NETFLIX
        ),
        arrayOf(
            Stock.FACEBOOK
        ),
        arrayOf(
            Stock.BMW
        ),
        arrayOf(
            Stock.TRIVAGO
        )
    )

    @Test
    @Parameters(method = "paramForSubscription")
    fun `when subscribe all is called then all stocks are subscribed to socket`(
        stock: Stock
    ) = runBlocking {
        // Given
        val stockRemote = mock<StockRemote> {
            on {
                events
            } doReturn flowOf(
                WebSocketEvent.ConnectionOpened,
                WebSocketEvent.ConnectionClosing(cause = "no stonk"),
                WebSocketEvent.MessageReceived
            )
        }

        val stockDataCache = mock<StockDataCache>().apply {
            whenever(data()) doReturn stockData()
            whenever(findById(stock.isin)) doReturn TestCacheModel(id = stock.isin)
        }

        val stockRepository = createStockRepository(
            stockRemote = stockRemote,
            stockDataCache = stockDataCache
        )

        // when
        stockRepository.subscribeAll()

        // then
        verify(stockRemote).subscribe(
            SubscribeData(isin = stock.isin)
        )
    }

    private fun createStockRepository(
        stockRemote: StockRemote = mock(),
        responseMapper: DataMapper = mock(),
        stockDataCache: StockDataCache = mock()
    ) = StockRepositoryImpl(
        stockRemote = stockRemote,
        mapper = responseMapper,
        stockDataCache = stockDataCache
    )
}
