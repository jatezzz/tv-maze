package com.jatezzz.tvmaze.di

import android.content.Context
import androidx.room.Room
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.service.AssetsService
import com.jatezzz.tvmaze.data.FavoriteShowDatabase
import com.jatezzz.tvmaze.data.FavoriteShowRepository
import com.jatezzz.tvmaze.data.FavoriteShowsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(
        favoriteShowsLocalDataSource: FavoriteShowsLocalDataSource,
    ): FavoriteShowRepository {
        return FavoriteShowRepository(favoriteShowsLocalDataSource)
    }

    @Provides
    @Singleton
    fun providesProductsLocalDataSource(
        database: FavoriteShowDatabase,
        ioDispatcher: CoroutineDispatcher
    ): FavoriteShowsLocalDataSource {
        return FavoriteShowsLocalDataSource(database, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesShowsRemoteDataSource(
        assetsService: AssetsService,
        ioDispatcher: CoroutineDispatcher
    ): ShowsRemoteDataSource {
        return ShowsRemoteDataSource(assetsService, ioDispatcher)
    }


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FavoriteShowDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteShowDatabase::class.java,
            "FavoriteShowDatabase.db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}