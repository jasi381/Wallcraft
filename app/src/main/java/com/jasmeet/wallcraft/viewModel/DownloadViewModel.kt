package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import com.jasmeet.wallcraft.model.repo.DownloadsDbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadRepo: DownloadRepo,
    private val downloadsDbRepo: DownloadsDbRepo
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _downloadedPhoto = MutableLiveData<List<DownloadsEntity>>()
    val downloadedPhoto: LiveData<List<DownloadsEntity>> get() = _downloadedPhoto

    fun startDownload(url: String, onDownloadComplete: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                downloadRepo.downloadFile(url)
                onDownloadComplete("Download Complete")
            } catch (e: Exception) {
                onDownloadComplete("Download Failed")
            } finally {
                _isLoading.value = false
            }
        }
    }

    init {
        getAllPhotos()
    }

    fun getAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _downloadedPhoto.postValue(downloadsDbRepo.getAllPhotos())
        }
    }

    fun insertPhoto(photo: DownloadsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadsDbRepo.insertPhoto(photo)
            getAllPhotos() // Refresh the list after insertion
        }
    }

    fun deletePhoto(photo: DownloadsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadsDbRepo.deletePhoto(photo)
            getAllPhotos() // Refresh the list after deletion
        }
    }

}