package com.jasmeet.wallcraft.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepo: DetailsRepo,
    @ApplicationContext private val context: Context

) : ViewModel() {

    private val _details: MutableStateFlow<DetailsApiResponse?> = MutableStateFlow(null)
    val details = _details.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()


    suspend fun getDetails(id: String) {
        try {
            val response = detailsRepo.getWallpaperDetails(id)
            _details.value = response
        } catch (e: Exception) {
            _error.value = "Failed to fetch details: ${e.message}"
        }

    }

//    fun shareImage(data: String?, shareImageLauncher: ActivityResultLauncher<Intent>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val bitmap = data?.let { Utils.getBitmapFromUrl(it) }
//            val byteArray = bitmap?.let { Utils.bitmapToByteArray(it) }
//
//            withContext(Dispatchers.Main) {
//                val shareIntent = Intent(Intent.ACTION_SEND).apply {
//                    type = "image/jpeg"
//                    putExtra(Intent.EXTRA_STREAM,byteArray)
//                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                }
//                shareImageLauncher.launch(Intent.createChooser(shareIntent,"Share Image"))
//            }
//        }
//    }
}