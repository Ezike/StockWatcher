package com.ezike.tobenna.lib_stock_price.data.mapper

import com.ezike.tobenna.lib_stock_price.domain.model.Stock
import com.ezike.tobenna.lib_stock_price.domain.model.StockData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.WebSocketEvent
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class DataMapperTest {

    fun paramsForMapper() = arrayOf(
        arrayOf(
            StockRemoteModel(
                isin = "US787899",
                price = 900.00,
                openPrice = 40.00
            ),
            WebSocketEvent.MessageReceived,
            StockResponse.Data(stockList = stockData())
        ),
        arrayOf(
            StockRemoteModel(
                isin = "US64110L1061",
                price = 154.33,
                openPrice = 40.00
            ),
            WebSocketEvent.MessageReceived,
            StockResponse.Data(
                stockList = stockWithPrice(
                    isin = "US64110L1061",
                    StockData(
                        "NETFLIX",
                        "US64110L1061", 154.33,
                        openPrice = 40.00
                    )
                )
            )
        ),
        arrayOf(
            StockRemoteModel(
                isin = "US787899",
                price = 900.00,
                openPrice = 40.00
            ),
            WebSocketEvent.ConnectionFailed(
                error = Throwable("Nope")
            ),
            StockResponse.Error(
                stockList = stockData(),
                message = "Nope"
            )
        ),
        arrayOf(
            StockRemoteModel(
                isin = "US787899",
                price = 900.00,
                openPrice = 40.00
            ),
            WebSocketEvent.ConnectionClosed(cause = "Naw"),
            StockResponse.Closed(
                stockList = stockData(),
                message = "Naw"
            )
        ),
        arrayOf(
            StockRemoteModel(
                isin = "US787899",
                price = 900.00,
                openPrice = 40.00
            ),
            WebSocketEvent.ConnectionClosing(cause = "Naw"),
            StockResponse.Closed(
                stockList = stockData(),
                message = "Naw"
            )
        ),
        arrayOf(
            StockRemoteModel(
                isin = "US787899",
                price = 900.00,
                openPrice = 40.00
            ),
            WebSocketEvent.ConnectionOpened,
            StockResponse.Data(
                stockList = stockData()
            )
        )
    )

    @Test
    @Parameters(method = "paramsForMapper")
    fun `when mapping to stock response then the correct data is returned`(
        model: StockRemoteModel,
        socketEvent: WebSocketEvent,
        expected: StockResponse
    ) {
        // Given
        val mapper = DataMapper()

        // when
        val actual = mapper.toStockResponse(
            data = expected.stockList,
            event = socketEvent
        )

        // then
        assertEquals(expected, actual)
    }

    // region helpers

    private fun stockData() = Stock.values().associate { security ->
        security.isin to StockData(
            name = security.name,
            price = 0.0,
            id = security.isin,
            openPrice = null
        )
    }.values.toList()

    private fun stockWithPrice(isin: String, data: StockData): List<StockData> {
        val map = Stock.values().associate { security ->
            security.isin to StockData(
                name = security.name,
                price = 0.0,
                id = security.isin,
                openPrice = 40.00
            )
        }.toMutableMap()
        map[isin] = data
        return map.values.toList()
    }

    // endregion
}
