package com.jatezzz.tvmaze.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.main.response.ShowsItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(private val dataSource: ShowsRemoteDataSource) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var showsList = listOf<ShowsItem>()

    fun loadShows() {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveList()
            _isLoading.value = false
        }
    }

    private suspend fun retrieveList() {
        val sceneResult = dataSource.fetchShowsList()
        val result = if (sceneResult is ResultType.Success) {
            sceneResult.data
        } else {
            arrayListOf()
        }
        showsList = result
    }

}