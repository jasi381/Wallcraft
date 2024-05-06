package com.jasmeet.wallcraft.view.screens

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity
import com.jasmeet.wallcraft.model.database.DownloadsDatabase
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    data: String?,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    id: String?
) {

    BackHandler {
        onBackClick.invoke()
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier.sharedElement(
            rememberSharedContentState(key = "image-$data"),
            animatedVisibilityScope,
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.img_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .blur(16.dp)
                .fillMaxSize()

        )

        Box(
            Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.75f)
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.img_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                filterQuality = FilterQuality.High,
                modifier = Modifier
                    .fillMaxSize()

            )
            Column(
                modifier = Modifier
                    .padding(bottom = 95.dp)
                    .align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconTonalButtonComponent(
                    icon = R.drawable.ic_fav_unselected,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(12.dp))

                IconTonalButtonComponent(
                    icon = R.drawable.ic_download,
                    onClick = {
                        downloadFile(data.toString(), context)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                IconTonalButtonComponent(
                    icon = R.drawable.ic_more,
                    onClick = {}
                )
            }
        }
    }


}


private fun downloadFile(url: String, context: Context) {
    val appName = context.getString(R.string.app_name)
    val storageDir =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName)
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }

    val fileName = "image.jpg" // Name of the file to be saved
    val file = File(storageDir, fileName)

    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle("Image Download") // Title of the Download Notification
        .setDescription("Downloading") // Description of the Download Notification
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
        .setAllowedOverMetered(true) //  if download is allowed on Mobile network
        .setAllowedOverRoaming(true) //  if download is allowed on roaming network
        .setDestinationUri(Uri.fromFile(file)) // Save downloaded file to the specified destination

    val downloadDao = DownloadsDatabase.getInstance(context).downloadDao()

    val coroutine = CoroutineScope(Dispatchers.IO)
    coroutine.launch {
        val imageBitmap = Utils.getBitmapFromUrl(url)

        val byteArray = imageBitmap?.let { Utils.bitmapToByteArray(it) }

        downloadDao.insertDownload(DownloadEntity(url, byteArray))
    }

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}


