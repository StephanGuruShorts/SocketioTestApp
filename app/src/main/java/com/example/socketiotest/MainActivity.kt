package com.example.socketiotest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.socketiotest.ui.theme.SocketIoTestTheme
import io.socket.client.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mSocket = IO.socket("http://localhost:3000")
        mSocket.connect()
        if (!mSocket.isActive) Log.d("rawr", "doesn't work")
        if (mSocket.isActive) Log.d("rawr", "works")

        setContent {
            SocketIoTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var textFromOnLogin: String = "my"

                    LaunchedEffect(key1 = null, block = {
                        mSocket.on("add user") {
                            textFromOnLogin = (it[0] as JSONObject).getInt("numUsers").toString()
                        }

                        mSocket.on("new message") {
                            textFromOnLogin =
                                (it[0] as JSONObject).getString("new message").toString()
                        }

                        mSocket.onAnyOutgoing() {
                            Log.d("rawr", "going")
                        }
                        mSocket.connect()
                    })

                    Column {

                        Button(onClick = { mSocket.emit("chat message", "android here") }) {
                            Text("sample button")
                        }

                        Button(onClick = {
                            // perform the user login attempt.

                            // perform the sending message attempt.
                            mSocket.emit("add user", "Man1")
                        }) {
                            Text(text = "\"addUser\", \"Man1\"")
                        }
                        Text(text = textFromOnLogin)
                        Button(onClick = {
                            // perform the user login attempt.

                            // perform the sending message attempt.
                            mSocket.emit("new message", "message")
                        }) {
                            Text(text = "\"new message\", \"message\"")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SocketIoTestTheme {
        Greeting("Android")
    }
}