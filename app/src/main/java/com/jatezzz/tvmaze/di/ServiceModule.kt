package com.jatezzz.tvmaze.di

import com.jatezzz.tvmaze.common.service.AssetsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

  @Provides
  @Singleton
  fun provideAssetsService(retrofit: Retrofit): AssetsService {
    return retrofit.create(AssetsService::class.java)
  }
}
