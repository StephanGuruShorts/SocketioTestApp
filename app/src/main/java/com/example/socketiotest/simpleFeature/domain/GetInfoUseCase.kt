package com.example.socketiotest.simpleFeature.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetInfoUseCase @Inject constructor(
    private val repository: SampleRepository
) {

    suspend fun invoke(): Result<Flow<String>> = withContext(Dispatchers.IO) {
        return@withContext repository.getInfo()
    }
}