package com.jatezzz.tvmaze.peoplelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.peoplelist.response.Person
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class PeopleListViewModel @Inject constructor(private val dataSource: ShowsRemoteDataSource) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _incomingShowPeopleList = MutableLiveData<List<Person>>()
    val incomingShowPeopleList: LiveData<List<Person>> = _incomingShowPeopleList

    fun filterByInput(text: String) {
        if (text.isEmpty()) {
            _incomingShowPeopleList.value = listOf()
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            retrievePeopleListByInput(text)
            _isLoading.value = false
        }
    }

    private suspend fun retrievePeopleListByInput(text: String) {
        val searchResult = dataSource.searchPeople(text)
        Timber.d(searchResult.toString())
        val result = if (searchResult is ResultType.Success) {
            searchResult.data
        } else {
            arrayListOf()
        }
        val people = result.map { it.person }
        _incomingShowPeopleList.value = people
    }

}