package com.jatezzz.tvmaze.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.person.response.CreditsItem
import com.jatezzz.tvmaze.show.DEFAULT_ID
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val dataSource: ShowsRemoteDataSource) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var personId: Int = DEFAULT_ID

    private val _incomingShow = MutableLiveData<ViewData>()
    val incomingEpisode: LiveData<ViewData> = _incomingShow

    fun saveArgs(episodeId: Int) {
        this.personId = episodeId
    }

    fun loadDetail() {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveDetail()
            _isLoading.value = false
        }
    }

    private suspend fun retrieveDetail() {
        if (personId == DEFAULT_ID) {
            return
        }
        val episodeResult = dataSource.retrievePersonById(personId)
        val data = if (episodeResult is ResultType.Success) {
            episodeResult.data
        } else {
            null
        }

        data?.let { it ->


            val creditResult = dataSource.retrieveCreditsByPersonId(personId)
            val credits = if (creditResult is ResultType.Success) {
                creditResult.data
            } else {
                null
            }
            credits?.let { _credits ->
                _incomingShow.value = ViewData(
                    it.id ?: DEFAULT_ID,
                    it.name ?: "",
                    it.image?.original ?: "",
                    _credits
                )
            }

        }
    }

    data class ViewData(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val credits: List<CreditsItem>,
    )

}