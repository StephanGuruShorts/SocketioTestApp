package com.example.socketiotest.simpleFeature.di

import com.example.socketiotest.aditional.AppLifecycleObserver
import com.example.socketiotest.aditional.SocketProvider
import com.example.socketiotest.simpleFeature.data.Config
import com.example.socketiotest.simpleFeature.data.SampleRepositoryImpl
import com.example.socketiotest.simpleFeature.domain.SampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SimpleModule {

    @Provides
    @Singleton
    fun provideSampleRepository(
        socketProvider: SocketProvider
    ): SampleRepository {
        val socket = socketProvider.getSocket(Config.BASE_URL)

        return SampleRepositoryImpl(socket)
    }

    @Provides
    @Singleton
    fun provideAppLifecycleObserver(): AppLifecycleObserver {
        return AppLifecycleObserver()
    }

    @Provides
    fun provideSocketProvider(
        appLifecycleObserver: AppLifecycleObserver
    ): SocketProvider {
        return SocketProvider(appLifecycleObserver)
    }
}