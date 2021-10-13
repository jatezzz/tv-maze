package com.jatezzz.tvmaze.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jatezzz.tvmaze.base.BaseViewModelFactory
import com.jatezzz.tvmaze.list.ListViewModel
import com.jatezzz.tvmaze.main.MainViewModel
import com.jatezzz.tvmaze.show.ShowViewModel
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
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    internal abstract fun listViewModel(viewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowViewModel::class)
    internal abstract fun showViewModel(viewModel: ShowViewModel): ViewModel

}