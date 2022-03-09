package com.ezike.tobenna.remote.service

import app.cash.turbine.test
import com.ezike.tobenna.remote.mapper.WebSocketEventMapper
import com.ezike.tobenna.remote.model.StockRemoteModel
import com.ezike.tobenna.remote.model.SubscribeData
import com.ezike.tobenna.remote.model.UnsubscribeData
import com.ezike.tobenna.remote.model.WebSocketEvent
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class StockRemoteTest {

    @Test
    fun `when events are requested then the correct data is returned`() = runTest {
        // Given
        val eventMapper = mock<WebSocketEventMapper> {
            on {
                toSocketEvent(
                    event = WebSocket.Event.OnMessageReceived(
                        message = Message.Text(value = "Stocks")
                    )
                )
            } doReturn WebSocketEvent.MessageReceived

            on {
                toSocketEvent(
                    event = WebSocket.Event.OnMessageReceived(
                        message = Message.Text(value = "Microsoft")
                    )
                )
            } doReturn WebSocketEvent.MessageReceived
        }

        val scarletStockService = mock<ScarletStockService> {
            on { events } doReturn flowOf(
                WebSocket.Event.OnMessageReceived(
                    message = Message.Text(value = "Stocks")
                ),
                WebSocket.Event.OnMessageReceived(
                    message = Message.Text(value = "Microsoft")
                )
            )
        }

        val stockRemote = createStockRemote(
            stockService = scarletStockService,
            eventMapper = eventMapper
        )

        // when
        val events = stockRemote.events

        // then
        events.test {
            assertEquals(
                WebSocketEvent.MessageReceived, awaitItem()
            )
            assertEquals(
                WebSocketEvent.MessageReceived, awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `when stock info is requested then the correct data is returned`() = runTest {
        // Given
        val scarletStockService = mock<ScarletStockService> {
            on { stockInfo } doReturn flowOf(
                StockRemoteModel(isin = "US1234567", price = 8999.22, openPrice = 0.0),
                StockRemoteModel(isin = "US12567", price = 8999.22, openPrice = 0.0)
            )
        }

        val stockRemote = createStockRemote(
            stockService = scarletStockService
        )

        // when
        val stockModel = stockRemote.stockInfo

        // then
        stockModel.test {
            assertEquals(
                StockRemoteModel(isin = "US1234567", price = 8999.22, openPrice = 0.0), awaitItem()
            )
            assertEquals(
                StockRemoteModel(isin = "US12567", price = 8999.22, openPrice = 0.0), awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `when subscribe is called then the provided data is subscribed to socket`() =
        runTest {
            // Given
            val scarletStockService = mock<ScarletStockService>()

            val stockRemote = createStockRemote(
                stockService = scarletStockService
            )

            // when
            stockRemote.subscribe(SubscribeData(isin = "YU8999999"))

            // then
            verify(scarletStockService).subscribe(SubscribeData(isin = "YU8999999"))
        }

    @Test
    fun `when unsubscribe is called then the provided data is unsubscribed from socket`() =
        runTest {
            // Given
            val scarletStockService = mock<ScarletStockService>()

            val stockRemote = createStockRemote(
                stockService = scarletStockService
            )

            // when
            stockRemote.unSubscribe(UnsubscribeData(isin = "YU8999999"))

            // then
            verify(scarletStockService).unSubscribe(UnsubscribeData(isin = "YU8999999"))
        }

    private fun createStockRemote(
        stockService: ScarletStockService = mock(),
        eventMapper: WebSocketEventMapper = mock()
    ) = StockRemoteImpl(
        scarletStockService = stockService,
        eventMapper = eventMapper
    )
}
