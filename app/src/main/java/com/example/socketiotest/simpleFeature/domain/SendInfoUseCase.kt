package com.example.socketiotest.simpleFeature.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendInfoUseCase @Inject constructor(
    private val repo: SampleRepository
) {

    suspend fun invoke(info: String) = withContext(Dispatchers.IO) {
        repo.sendInfo(info = info)
    }
}