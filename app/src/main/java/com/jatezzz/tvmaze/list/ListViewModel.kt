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


    private val _incomingShowList = MutableLiveData<ListViewData>()
    private val _showList = ArrayList<ShowsItem>()
    val incomingShowList: LiveData<ListViewData> = _incomingShowList

    private var currentPage: Int = 0

    fun loadShows(page: Int = 0) {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveList(page)
            Timber.d("List size: ${_incomingShowList.value?.shows?.size}")
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
        _incomingShowList.value = ListViewData(result, true)
        _showList.addAll(result)
    }

    fun loadMoreShows() {
        if (_incomingShowList.value?.shows?.isEmpty() == false && _isLoading.value == false) {
            currentPage++
            loadShows(currentPage)
        }
    }

    fun filterByInput(text: String) {
        if (text.isEmpty()) {
            _incomingShowList.value = ListViewData(_showList, false)
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            retrieveListByInput(text)
            _isLoading.value = false
        }
    }

    private suspend fun retrieveListByInput(text: String) {
        val searchResult = dataSource.retrieveListByInput(text)
        Timber.d(searchResult.toString())
        val result = if (searchResult is ResultType.Success) {
            searchResult.data
        } else {
            arrayListOf()
        }
        val shows = result.map { it.show }
        _incomingShowList.value = ListViewData(shows, false)
    }

    data class ListViewData(
        val shows: List<ShowsItem>,
        val hasAppendableData: Boolean
    )
}