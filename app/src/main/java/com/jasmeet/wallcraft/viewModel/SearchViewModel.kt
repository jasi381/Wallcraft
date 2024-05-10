package com.jasmeet.wallcraft.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jasmeet.wallcraft.model.apiResponse.remote.Photo
import com.jasmeet.wallcraft.model.pagingSource.SearchPagingSource
import com.jasmeet.wallcraft.model.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepo
) : ViewModel() {

    private val _searchedResults: MutableStateFlow<PagingData<Photo>> =
        MutableStateFlow(PagingData.empty())
    val searchedResults = _searchedResults.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private var currentQuery: String? = null

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    init {
        viewModelScope.launch {
            _query.collect { query ->
                if (query.isNotEmpty()) {
                    loadPhotos(query)
                }
            }
        }
    }

    fun loadPhotos(query: String) {
        viewModelScope.launch {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = { SearchPagingSource(searchRepo = repo, query = query) }
                ).flow
                    .cachedIn(viewModelScope)
                    .collectLatest { results ->
                        _searchedResults.value = results
                        Log.d(
                            "SearchViewModel",
                            "Wallpapers loaded successfully: $results"
                        )
                    }
            } catch (e: Exception) {
                _error.value = "Failed to load movies: ${e.message}"
                Log.e("SearchMovieViewModel", "Error loading movies", e)
            }
        }
    }

    fun retry() {
        currentQuery?.let(::loadPhotos)
    }

    fun clearQuery() {
        currentQuery = null
        _query.value = ""
        _searchedResults.value = PagingData.empty()
    }
}
