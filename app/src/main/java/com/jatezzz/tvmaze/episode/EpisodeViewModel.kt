package com.jatezzz.tvmaze.episode

import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.show.DEFAULT_ID
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(private val dataSource: ShowsRemoteDataSource) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var episodeId: Int = DEFAULT_ID

    private val _incomingShow = MutableLiveData<EpisodeViewData>()
    val incomingEpisode: LiveData<EpisodeViewData> = _incomingShow

    fun saveArgs(episodeId: Int) {
        this.episodeId = episodeId
    }

    fun loadDetail() {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveDetail()
            _isLoading.value = false
        }
    }

    private suspend fun retrieveDetail() {
        if (episodeId == DEFAULT_ID) {
            return
        }
        val episodeResult = dataSource.retrieveEpisodeDetail(episodeId)
        val data = if (episodeResult is ResultType.Success) {
            episodeResult.data
        } else {
            null
        }

        data?.let { it ->
            _incomingShow.value = EpisodeViewData(
                it.id ?: DEFAULT_ID,
                it.image?.original ?: "",
                it.name ?: "",
                it.number.toString(),
                it.season.toString(),
                HtmlCompat.fromHtml(it.summary ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
            )
        }
    }

    data class EpisodeViewData(
        val id: Int,
        val imageUrl: String,
        val name: String,
        val number: String,
        val season: String,
        val summary: Spanned
    )

}