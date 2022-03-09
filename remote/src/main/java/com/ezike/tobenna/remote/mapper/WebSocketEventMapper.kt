package com.ezike.tobenna.remote.mapper

import com.ezike.tobenna.remote.model.WebSocketEvent
import com.tinder.scarlet.WebSocket
import javax.inject.Inject

internal class WebSocketEventMapper @Inject constructor() {
    fun toSocketEvent(event: WebSocket.Event): WebSocketEvent = when (event) {
        is WebSocket.Event.OnConnectionOpened<*> -> WebSocketEvent.ConnectionOpened
        is WebSocket.Event.OnMessageReceived -> WebSocketEvent.MessageReceived
        is WebSocket.Event.OnConnectionClosing -> WebSocketEvent.ConnectionClosing(cause = event.shutdownReason.reason)
        is WebSocket.Event.OnConnectionClosed -> WebSocketEvent.ConnectionClosed(cause = event.shutdownReason.reason)
        is WebSocket.Event.OnConnectionFailed -> WebSocketEvent.ConnectionFailed(error = event.throwable)
    }
}
