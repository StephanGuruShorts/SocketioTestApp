package com.example.socketiotest.simpleFeature.domain

import kotlinx.coroutines.flow.Flow


interface SampleRepository {

    suspend fun getInfo(): Result<Flow<String>>
    suspend fun sendInfo(info: String)
}