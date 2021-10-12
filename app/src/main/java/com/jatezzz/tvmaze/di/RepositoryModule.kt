package com.jatezzz.tvmaze.di

import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.service.AssetsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesScenesRemoteDataSource(
        assetsService: AssetsService,
        ioDispatcher: CoroutineDispatcher
    ): ShowsRemoteDataSource {
        return ShowsRemoteDataSource(assetsService, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}