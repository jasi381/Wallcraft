package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.repo.DownloadRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadRepo: DownloadRepo
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun startDownload(url: String) {
        viewModelScope.launch {
            _isLoading.value = true
            downloadRepo.downloadFile(url)
            _isLoading.value = false
        }
    }
}