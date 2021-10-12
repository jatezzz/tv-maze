package com.jatezzz.tvmaze.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import com.jatezzz.tvmaze.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SerializationModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)

        if (BuildConfig.DEBUG) {
            builder.setPrettyPrinting()
        }

        return builder.create()
    }

}
