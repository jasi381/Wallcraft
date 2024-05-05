package com.jasmeet.wallcraft.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jasmeet.wallcraft.model.apiResponse.remote.Photo
import com.jasmeet.wallcraft.model.pagingSource.HomePagingSource
import com.jasmeet.wallcraft.model.repo.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo
) : ViewModel() {

    private val _homeData: MutableStateFlow<PagingData<Photo>> =
        MutableStateFlow(PagingData.empty())
    val homeData = _homeData.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 70,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = { HomePagingSource(homeRepo) },

                    ).flow
                    .cachedIn(viewModelScope)
                    .collectLatest { filteredNowPlayingMovies ->
                        _homeData.value = filteredNowPlayingMovies
                    }
            } catch (e: Exception) {
                // Handle the exception here
                Log.e("HomeViewModel", "Error loading data: ${e.message}", e)
            }
        }
    }

    init {
        loadData()
    }
}
