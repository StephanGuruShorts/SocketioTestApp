package com.example.socketiotest

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.socket.client.Ack
import io.socket.client.Socket

data class Message(
    val from: String,
    val value: String
)

@Composable
fun SampleFirstScreen(
    socket: Socket
) {

    val listMessages = remember {
        mutableStateOf(listOf<Message>())
    }

    LaunchedEffect(key1 = null) {
        socket.on("hi") {
            listMessages.add(Message("server", "${it[0]}"))
        }

        socket.on("args") {
            listMessages.add(Message("server", "${it.toList()}"))
        }

        socket.on("ack from server") {
            listMessages.add(Message("server", "it[0] is Ack = ${it[0] is Ack}"))
            (it[0] as Ack).call("hi server")
            listMessages.add(Message("client", "\"hi server\""))
        }

        socket.on("info") {
            Log.d("rawr","info recieved")
        }
    }

    Row {
        Column {
            Button(onClick = {
                socket.emit("hi", "hi from client")
                listMessages.add(Message("client", "hi from client"))
            }) {
                Text(text = "Say hi")
            }

            Button(onClick = {
                socket.emit("args", "1", 2, 3.0, Message("me", "ssage"))
                listMessages.add(Message("client", "\"1\", 2, 3.0, Message(\"me\", \"ssage\")"))
            }) {
                Text(text = "Say args")
            }

            Button(onClick = {
                socket.emit("ack from client", "2", 3, object : Ack {
                    override fun call(vararg args: Any?) {
                        val response = args[0]
                        listMessages.add(Message("client response from server", "$response"))
                    }
                })
                listMessages.add(Message("client", "\"2\", 3, object : Ack"))
            }) {
                Text(text = "ack from client")
            }

            Button(onClick = {
                socket.emit("ack from server init", "ack me")
                listMessages.add(Message("client", "ack me"))
            }) {
                Text(text = "ack from server")
            }
        }

        Column {
            LazyColumn() {
                items(listMessages.value) {
                    Card {
                        Row {
                            Text(modifier = Modifier.weight(1f), text = it.from)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(modifier = Modifier.weight(1f), text = it.value)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

fun MutableState<List<Message>>.add(message: Message) {
    val newList: MutableList<Message> = mutableListOf<Message>()
    newList.addAll(this.value)
    newList.add(message)
    this.value = newList
}