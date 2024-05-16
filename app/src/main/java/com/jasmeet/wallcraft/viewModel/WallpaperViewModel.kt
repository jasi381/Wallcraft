package com.jasmeet.wallcraft.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.model.repo.WallpaperRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel
@Inject constructor(
    private val repo: WallpaperRepo
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setWallpaperAndHandleLoading(data: String?, wallpaperType: WallpaperType) {
        _isLoading.value = true
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                data?.let { repo.loadBitmapFromUrl(it) }
            }
            repo.setWallpaper(bitmap, wallpaperType)
            _isLoading.value = false
        }
    }
}