package com.example.socketiotest.aditional

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.example.socketiotest.BuildConfig
import io.socket.client.IO
import io.socket.client.IO.Options
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SocketProvider(
    private val appLifecycleObserver: AppLifecycleObserver
) {

    private var socket: Socket? = null

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun getSocket(url: String, options: Options = Options()): Socket {
        return if (socket == null) {
            socket = IO.socket(url, options)
            socket?.connect()
            initScope()
            socket!!
        } else {
            socket!!
        }
    }

    private fun initScope() {

        //handle lifecycle connect/disconnect
        coroutineScope.launch {
            appLifecycleObserver.eventState.collect {
                Log.d("rawr1", "$it")
                when (it) {
                    Lifecycle.Event.ON_CREATE -> {
                    }

                    Lifecycle.Event.ON_START -> {
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        Log.d("rawr1", "Lifecycle.Event.ON_RESUME")
                        if (socket?.connected() == false) {
                            socket?.connect()
                        }
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                    }

                    Lifecycle.Event.ON_STOP -> {
                        Log.d("rawr1", "Lifecycle.Event.ON_STOP")
                        socket?.disconnect()
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                    }

                    Lifecycle.Event.ON_ANY -> {
                    }
                }
            }
        }

        //handle service information
        coroutineScope.launch {
            socket?.let { socket ->
                socket.on(Socket.EVENT_CONNECT) {
                }

                socket.on(Socket.EVENT_DISCONNECT) {
                }

                socket.on(Socket.EVENT_CONNECT_ERROR) {
                }

                if (BuildConfig.DEBUG) {
                    socket.onAnyOutgoing {
                        Log.d("rawr1", "send = $it")
                    }

                    socket.onAnyIncoming {
                        Log.d("rawr1", "receive = $it")
                    }
                }
            }
        }
    }
}