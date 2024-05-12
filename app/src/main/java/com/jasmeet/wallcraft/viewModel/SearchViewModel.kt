package com.jasmeet.wallcraft.viewModel

//@HiltViewModel
//class SearchViewModel @Inject constructor(
//    private val repo: SearchRepo
//) : ViewModel() {
//
//    private val _searchedResults: MutableStateFlow<PagingData<Photo>> =
//        MutableStateFlow(PagingData.empty())
//    val searchedResults = _searchedResults.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> get() = _error
//
//    private var currentQuery: String? = null
//
//    private val _query = MutableStateFlow("")
//    val query: StateFlow<String> get() = _query
//
//    init {
//        viewModelScope.launch {
//            _query.collect { query ->
//                if (query.isNotEmpty()) {
//                    loadPhotos(query)
//                }
//            }
//        }
//    }
//
//    fun loadPhotos(query: String) {
//        viewModelScope.launch {
//            try {
//                Pager(
//                    config = PagingConfig(
//                        pageSize = 10,
//                        enablePlaceholders = true
//                    ),
//                    pagingSourceFactory = { SearchPagingSource(searchRepo = repo, query = query) }
//                ).flow
//                    .cachedIn(viewModelScope)
//                    .collectLatest { results ->
//                        _searchedResults.value = results
//                        Log.d(
//                            "SearchViewModel",
//                            "Wallpapers loaded successfully: $results"
//                        )
//                    }
//            } catch (e: Exception) {
//                _error.value = "Failed to load movies: ${e.message}"
//                Log.e("SearchMovieViewModel", "Error loading movies", e)
//            }
//        }
//    }
//
//    fun retry() {
//        currentQuery?.let(::loadPhotos)
//    }
//
//    fun clearQuery() {
//        currentQuery = null
//        _query.value = ""
//        _searchedResults.value = PagingData.empty()
//    }
//}
