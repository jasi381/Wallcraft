package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse.PhotographerPhotosApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse.PostedByApiResponse
import com.jasmeet.wallcraft.model.repo.PhotographerPhotosRepo
import com.jasmeet.wallcraft.model.repo.PostedByRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotographerDetailsViewModel
@Inject constructor(
    private val postedByRepo: PostedByRepo,
    private val photographerPhotosRepo: PhotographerPhotosRepo
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _details: MutableStateFlow<PostedByApiResponse?> = MutableStateFlow(null)
    val details = _details.asStateFlow()

    private val _photos: MutableStateFlow<List<PhotographerPhotosApiResponse?>> =
        MutableStateFlow(emptyList())
    val photos = _photos.asStateFlow()

    private val _error: MutableStateFlow<String> = MutableStateFlow("")
    val error = _error.asStateFlow()


    fun getDetails(name: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = postedByRepo.getPhotographerDetails(name)
                _details.value = response
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message.toString()
                _isLoading.value = false
            }

        }
    }


    fun getPhotos(name: String, orderBy: String = OrderBy.LATEST.displayName) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = photographerPhotosRepo.getPhotos(name, orderBy)
                _photos.value = response

            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message.toString()
                _isLoading.value = false
            }
        }
    }


}