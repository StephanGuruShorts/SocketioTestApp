package com.example.socketiotest.aditional

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppLifecycleObserver: LifecycleEventObserver {

    private val _eventState = MutableStateFlow(Lifecycle.Event.ON_START)
    val eventState: StateFlow<Lifecycle.Event> = this._eventState

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        _eventState.value = event
    }
}