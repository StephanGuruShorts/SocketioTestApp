package com.example.socketiotest.simpleFeature.data

import com.example.socketiotest.aditional.on
import com.example.socketiotest.simpleFeature.domain.SampleRepository
import io.socket.client.Socket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val socket: Socket
) : SampleRepository {

    override suspend fun getInfo(): Result<Flow<String>> {
        return kotlin.runCatching {
            socket.on(INFO_EVENT).map { it[0] as String }
        }
    }

    override suspend fun sendInfo(info: String) {
        socket.emit(INFO_EVENT, info)
    }

    companion object {
        const val INFO_EVENT = "info"
    }
}