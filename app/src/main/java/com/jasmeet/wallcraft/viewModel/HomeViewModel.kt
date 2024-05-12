package com.jasmeet.wallcraft.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import com.jasmeet.wallcraft.model.pagingSource.HomePagingSource
import com.jasmeet.wallcraft.model.repo.HomeRepo
import com.jasmeet.wallcraft.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _homeData: MutableStateFlow<PagingData<HomeApiResponse>> =
        MutableStateFlow(PagingData.empty())
    val homeData = _homeData.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            loadData()
        }

        override fun onLost(network: Network) {
            _error.value = "No Internet Connection"
        }
    }

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Checking initial network state
        if (Utils.isNetworkAvailable(context)) {
            loadData()
        } else {
            _error.value = "No Internet Connection"
        }
    }

    private fun loadData(){
        viewModelScope.launch {
            try{
                Pager(
                    config = PagingConfig(
                        pageSize = 70,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = {HomePagingSource(homeRepo)}
                ).flow
                    .cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .collectLatest {
                        _homeData.value = it
                        _error.value = null
                    }
            }catch (e:Exception){
                _error.value = e.message
                Log.e("HomeViewModel", "Error loading data: ${e.message}", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
