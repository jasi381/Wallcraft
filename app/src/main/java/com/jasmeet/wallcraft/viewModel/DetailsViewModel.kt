package com.jasmeet.wallcraft.viewModel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.repo.DetailsRepo
import com.jasmeet.wallcraft.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
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


    fun getDetails(id: String) {
        viewModelScope.launch {
            try {
                val response = detailsRepo.getWallpaperDetails(id)
                _details.value = response
            } catch (e: Exception) {
                _error.value = "Failed to fetch details: ${e.message}"
            }
        }
    }

    fun shareImage(data: String?, shareImageLauncher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = data?.let { Utils.getBitmapFromUrl(it) }

            val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imagesDir, "shared_images.jpeg")
            FileOutputStream(imageFile).use { outputStream ->
                bitmap?.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    outputStream
                )
            }
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )

            withContext(Dispatchers.Main) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, uri as Parcelable)
                    putExtra(Intent.EXTRA_TEXT, "Shared from WallCraft")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                val chooser = Intent.createChooser(shareIntent, "Share Image")
                shareImageLauncher.launch(chooser)
            }
        }
    }
}