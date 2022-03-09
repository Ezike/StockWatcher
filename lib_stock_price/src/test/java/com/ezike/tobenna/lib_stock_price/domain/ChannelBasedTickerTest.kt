package com.ezike.tobenna.lib_stock_price.domain

import app.cash.turbine.test
import com.ezike.tobenna.lib_stock_price.TimeSource
import com.ezike.tobenna.lib_stock_price.domain.model.StockResponse
import com.ezike.tobenna.lib_stock_price.handler.ChannelBasedTicker
import com.ezike.tobenna.lib_stock_price.stockData
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(JUnitParamsRunner::class)
internal class ChannelBasedTickerTest {

    fun paramForElapsedTime() = arrayOf(
        arrayOf(
            4L,
            ChannelBasedTicker.Start(
                startTime = 2L,
                event = StockResponse.Error(
                    stockList = stockData(),
                    message = "No stonks"
                )
            ),
            Pair(
                2L,
                StockResponse.Error(
                    stockList = stockData(),
                    message = "No stonks"
                )
            )
        ),
        arrayOf(
            40L,
            ChannelBasedTicker.Start(
                startTime = 20L,
                event = StockResponse.Data(
                    stockList = stockData()
                )
            ),
            Pair(
                20L,
                StockResponse.Data(
                    stockList = stockData()
                )
            )
        )
    )

    @Test
    @Parameters(method = "paramForElapsedTime")
    fun `when elapsed time is requested then the correct data is returned`(
        currentTime: Long,
        tickerEvent: ChannelBasedTicker.TickerEvent,
        expected: Pair<Long, StockResponse>
    ) = runTest {
        // Given
        val timeSource = mock<TimeSource> {
            on { time } doReturn currentTime
        }

        val elapsedTimeTicker = ChannelBasedTicker(
            timeSource = timeSource
        )

        val channel = Channel<ChannelBasedTicker.TickerEvent>()

        // when
        val elapsedTime = elapsedTimeTicker.start(this, channel)
        channel.send(tickerEvent)

        // then
        elapsedTime.receiveAsFlow().test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `when elapsed time ticker is stopped then no data is returned`() = runTest {
        // Given
        val elapsedTimeTicker = ChannelBasedTicker(timeSource = mock())

        val channel = Channel<ChannelBasedTicker.TickerEvent>()

        // when
        val elapsedTime = elapsedTimeTicker.start(this, channel)
        channel.send(
            ChannelBasedTicker.Start(
                startTime = 20L,
                event = StockResponse.Data(
                    stockList = stockData()
                )
            )
        )
        channel.send(ChannelBasedTicker.Stop)

        // then
        elapsedTime.receiveAsFlow().test {
            expectNoEvents()
        }
    }
}
