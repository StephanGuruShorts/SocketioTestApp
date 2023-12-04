package com.example.socketiotest.aditional

import io.socket.client.Socket
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


fun Socket.on(event: String): Flow<Array<out Any>> {
    val channel: Channel<Array<out Any>> = Channel()

    this.on(event) {
        channel.trySend(it)
    }

    return channel.receiveAsFlow()
}