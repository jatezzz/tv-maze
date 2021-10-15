package com.jatezzz.tvmaze.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.data.FavoriteShow
import com.jatezzz.tvmaze.data.FavoriteShowRepository
import com.jatezzz.tvmaze.list.response.ShowsItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(private val favoriteShowRepository: FavoriteShowRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _incomingShowList = MutableLiveData<ViewData>()
    val incomingShowList: LiveData<ViewData> = _incomingShowList


    var savedShows: List<FavoriteShow> = listOf()

    fun loadShows() {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveList()
            _isLoading.value = false
        }
    }

    private suspend fun retrieveList() {
        val sceneResult = favoriteShowRepository.retrieveFavoriteShowList()
        val result = if (sceneResult is ResultType.Success) {
            sceneResult.data
        } else {
            arrayListOf()
        }
        savedShows = result.sortedBy { it.name }
        _incomingShowList.value = ViewData(savedShows)
    }

    fun remove(show: ShowsItem) {
        viewModelScope.launch {
            show.id?.let { favoriteShowRepository.removeFavoriteShow(it) }
            loadShows()
        }

    }

    fun filterByInput(text: String) {
        if (text.isEmpty()) {
            _incomingShowList.value = ViewData(savedShows)
            return
        }
        if (text.length <= 3) {
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            retrieveListByInput(text)
            _isLoading.value = false
        }
    }

    private fun retrieveListByInput(text: String) {
        val shows = savedShows.filter { it.name.startsWith(text, true) }.sortedBy { it.name }
        _incomingShowList.value = ViewData(shows)
    }

    data class ViewData(
        val shows: List<FavoriteShow>
    )
}