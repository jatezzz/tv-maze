package com.jatezzz.tvmaze.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.base.BaseViewModelFactory
import com.jatezzz.tvmaze.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun solutionViewModel(viewModel: MainViewModel): ViewModel

}