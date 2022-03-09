package com.ezike.tobenna.remote.mapper

import com.ezike.tobenna.remote.model.WebSocketEvent
import com.tinder.scarlet.Message
import com.tinder.scarlet.ShutdownReason
import com.tinder.scarlet.WebSocket
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock

@RunWith(JUnitParamsRunner::class)
internal class WebSocketEventMapperTest {

    fun paramForEventMap() = arrayOf(
        arrayOf(
            WebSocket.Event.OnConnectionOpened(mock()),
            WebSocketEvent.ConnectionOpened
        ),
        arrayOf(
            WebSocket.Event.OnMessageReceived(
                message = Message.Text(value = "Stonks")
            ),
            WebSocketEvent.MessageReceived
        ),
        arrayOf(
            WebSocket.Event.OnMessageReceived(
                message = Message.Bytes(value = "Hooge Stonks".toByteArray())
            ),
            WebSocketEvent.MessageReceived
        ),
        arrayOf(
            WebSocket.Event.OnConnectionClosing(
                ShutdownReason(code = 11, reason = "No stonks")
            ),
            WebSocketEvent.ConnectionClosing(cause = "No stonks")
        ),
        arrayOf(
            WebSocket.Event.OnConnectionClosed(
                ShutdownReason(code = 11, reason = "No stonks")
            ),
            WebSocketEvent.ConnectionClosed(cause = "No stonks")
        )
    )

    @Test
    @Parameters(method = "paramForEventMap")
    fun `when socket event is mapped then the correct data is returned`(
        event: WebSocket.Event,
        expected: WebSocketEvent
    ) {
        // Given
        val eventMapper = WebSocketEventMapper()

        // when
        val actual = eventMapper.toSocketEvent(event)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `when socket event is connection failed then the correct data is returned`() {
        // Given
        val eventMapper = WebSocketEventMapper()

        // when
        val actual = eventMapper.toSocketEvent(
            WebSocket.Event.OnConnectionFailed(Throwable("No stonks"))
        ) as WebSocketEvent.ConnectionFailed

        val expected = WebSocketEvent.ConnectionFailed(error = Throwable("No stonks"))

        // then
        assertEquals(expected.error.message, actual.error.message)
    }
}
