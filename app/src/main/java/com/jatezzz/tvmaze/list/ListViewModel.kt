package com.jatezzz.tvmaze.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.list.response.ShowsItem
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class ListViewModel @Inject constructor(private val dataSource: ShowsRemoteDataSource) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _incomingShowList = MutableLiveData<List<ShowsItem>>()
    private val _showList = ArrayList<ShowsItem>()
    val incomingShowList: LiveData<List<ShowsItem>> = _incomingShowList

    private var currentPage: Int = 0

    fun loadShows(page: Int = 0) {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveList(page)
            Timber.d("List size: ${_incomingShowList.value?.size}")
            _isLoading.value = false
        }
    }

    private suspend fun retrieveList(page: Int = 0) {
        val sceneResult = dataSource.fetchShowsList(page)
        val result = if (sceneResult is ResultType.Success) {
            sceneResult.data
        } else {
            arrayListOf()
        }
        _incomingShowList.value = result
        _showList.addAll(result)
    }

    fun loadMoreShows() {
        if (_incomingShowList.value?.isEmpty() == false && _isLoading.value == false) {
            currentPage++
            loadShows(currentPage)
        }
    }

}