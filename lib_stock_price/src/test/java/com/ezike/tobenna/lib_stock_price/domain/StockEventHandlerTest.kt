package com.ezike.tobenna.lib_stock_price.domain

import app.cash.turbine.test
import com.ezike.tobenna.lib_stock_price.TimeSource
import com.ezike.tobenna.lib_stock_price.domain.model.EventData
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.lib_stock_price.handler.ChannelBasedTicker
import com.ezike.tobenna.lib_stock_price.handler.StockEventHandler
import com.ezike.tobenna.lib_stock_price.stockData
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(JUnitParamsRunner::class)
internal class StockEventHandlerTest {

    fun paramForEvent() = arrayOf(
        arrayOf(
            StockResponse.Error(
                stockList = stockData(),
                message = "No stonks"
            ),
            2L,
            4L,
            2L,
            true
        ),
        arrayOf(
            StockResponse.Closed(
                stockList = stockData(),
                message = "No stonks"
            ),
            1L,
            9L,
            8L,
            false
        )
    )

    @Test
    @Parameters(method = "paramForEvent")
    fun `when handle is called then the correct data is returned`(
        event: StockResponse,
        errorTime: Long,
        currentTime: Long,
        elapsedTime: Long,
        isError: Boolean
    ) = runTest {
        // Given
        val errorTimeSource = mock<TimeSource> {
            on { time } doReturn errorTime
        }

        val currentTimeSource = mock<TimeSource> {
            on { time } doReturn currentTime
        }

        val stockEventHandler = createEventHandler(
            timeTicker = ChannelBasedTicker(timeSource = currentTimeSource),
            timeSource = errorTimeSource
        )

        // when
        val eventData = stockEventHandler.handle(this, flowOf(event))

        // then
        eventData.test {
            assertEquals(
                EventData(isError = isError, time = elapsedTime),
                awaitItem()
            )
        }
    }

    @Test
    fun `when handle is called with Data event then the correct data is returned`() =
        runTest {
            // Given
            val stockEventHandler = createEventHandler(
                timeTicker = ChannelBasedTicker(timeSource = mock()),
            )

            // when
            val data = flowOf(
                StockResponse.Data(stockList = stockData())
            )

            val eventData = stockEventHandler.handle(
                coroutineScope = this,
                data = data
            )

            // then
            eventData.test {
                expectNoEvents()
            }
        }

    private fun createEventHandler(
        timeTicker: ChannelBasedTicker = mock(),
        timeSource: TimeSource = mock()
    ) = StockEventHandler(
        ticker = timeTicker,
        timeSource = timeSource
    )
}
