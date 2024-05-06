package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity
import com.jasmeet.wallcraft.model.database.DownloadsDatabase
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.TextComponent

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var downloadedImages by remember {
        mutableStateOf<MutableList<DownloadEntity>>(mutableListOf())
    }

    LaunchedEffect(true) {

        val downloadDao = DownloadsDatabase.getInstance(context).downloadDao()
        downloadedImages = downloadDao.getAllDownloads().toMutableList()

    }

    LazyColumn {
        items(downloadedImages.size) {
            val convertByteArrayToBitmap = downloadedImages[it].imageBytes?.let { it1 ->
                Utils.byteArrayToBitmap(
                    it1
                )
            }
            TextComponent(text = downloadedImages[it].url)
            convertByteArrayToBitmap?.let { it1 ->
                Image(
                    bitmap = it1.asImageBitmap(),
                    contentDescription = null
                )
            }
        }
    }

}