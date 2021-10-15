package com.jatezzz.tvmaze.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatezzz.tvmaze.common.ShowsRemoteDataSource
import com.jatezzz.tvmaze.common.retrofit.ResultType
import com.jatezzz.tvmaze.data.FavoriteShowRepository
import com.jatezzz.tvmaze.list.response.ShowsItem
import com.jatezzz.tvmaze.show.response.EpisodeItem
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_ID = -1

class ShowViewModel @Inject constructor(
    private val dataSource: ShowsRemoteDataSource,
    private val favoriteShowRepository: FavoriteShowRepository
) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var showId: Int = DEFAULT_ID
    private var currentShow: ShowsItem? = null

    private val _incomingShow = MutableLiveData<ShowViewData>()
    val incomingShow: LiveData<ShowViewData> = _incomingShow


    fun loadDetail() {
        _isLoading.value = true
        viewModelScope.launch {
            retrieveDetail()
            _isLoading.value = false
        }
    }

    private suspend fun retrieveDetail() {
        if (showId == DEFAULT_ID) {
            return
        }
        val showResult = dataSource.retrieveShowDetail(showId)
        currentShow = if (showResult is ResultType.Success) {
            showResult.data
        } else {
            null
        }
        val episodeResult = dataSource.retrieveShowEpisodes(showId)
        val episodeData = if (episodeResult is ResultType.Success) {
            episodeResult.data
        } else {
            null
        }
        val favResult = favoriteShowRepository.retrieveFavoriteById(showId.toLong())
        val favData = if (favResult is ResultType.Success) {
            favResult.data
        } else {
            null
        }
        currentShow?.let { it ->
            val schedule =
                "See it on: ${it.schedule?.days?.joinToString(separator = ", ")}. At: ${it.schedule?.time}"
            _incomingShow.value = ShowViewData(
                genres = it.genres ?: listOf(),
                episodes = retrieveEpisodes(episodeData),
                imageUrl = it.image?.original ?: "",
                name = it.name ?: "",
                schedule = schedule,
                summary = it.summary ?: "",
                isFavorite = favData != null
            )
        }
    }

    private fun retrieveEpisodes(episodeData: List<EpisodeItem>?) =
        episodeData?.map { episodeItem ->
            EpisodeViewData(
                episodeItem.id ?: DEFAULT_ID,
                episodeItem.image?.medium ?: "",
                episodeItem.name ?: "",
                episodeItem.season ?: 0
            )
        }?.groupBy { data -> data.season }?.toList() ?: listOf()

    fun saveArgs(showId: Int) {
        this.showId = showId
    }

    fun toggleFavorite() {
        val previousData = _incomingShow.value ?: return
        val currentShow = currentShow ?: return
        viewModelScope.launch {
            if (!previousData.isFavorite) {
                favoriteShowRepository.saveFavoriteShow(currentShow)
            } else {
                currentShow.id?.let { favoriteShowRepository.removeFavoriteShow(it) }
            }
            _incomingShow.value =
                previousData.copy().apply { isFavorite = !previousData.isFavorite }
        }

    }

    data class ShowViewData(
        val genres: List<String>,
        var episodes: List<Pair<Int, List<EpisodeViewData>>>,
        val imageUrl: String,
        val name: String,
        val schedule: String,
        val summary: String,
        var isFavorite: Boolean,
    )

    data class EpisodeViewData(
        val id: Int,
        val imageUrl: String,
        val name: String,
        val season: Int
    )
}