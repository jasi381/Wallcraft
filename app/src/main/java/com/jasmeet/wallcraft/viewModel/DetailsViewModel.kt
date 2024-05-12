package com.jasmeet.wallcraft.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepo: DetailsRepo
) : ViewModel() {

    private val _details: MutableStateFlow<DetailsApiResponse?> = MutableStateFlow(null)
    val details = _details.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()


    suspend fun getDetails(id: String) {
        try {
            val response = detailsRepo.getWallpaperDetails(id)
            _details.value = response
            Log.d("DetailsViewModel", "getDetails: $response")
        } catch (e: Exception) {
            _error.value = "Failed to fetch details: ${e.message}"
            Log.d("DetailsViewModel", "getDetails: ${e.message}")
        }

    }
}