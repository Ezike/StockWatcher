package com.ezike.tobenna.remote.model

public sealed interface WebSocketEvent {
    public object ConnectionOpened : WebSocketEvent
    public object MessageReceived : WebSocketEvent
    public data class ConnectionClosing(val cause: String) : WebSocketEvent
    public data class ConnectionClosed(val cause: String) : WebSocketEvent
    public data class ConnectionFailed(val error: Throwable) : WebSocketEvent
}
