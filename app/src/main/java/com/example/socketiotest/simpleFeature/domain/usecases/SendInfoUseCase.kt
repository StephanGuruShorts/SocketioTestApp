package com.example.socketiotest.simpleFeature.domain.usecases

import com.example.socketiotest.simpleFeature.domain.SampleRepository
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