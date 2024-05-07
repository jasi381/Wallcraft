package com.jasmeet.wallcraft.view.screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity
import com.jasmeet.wallcraft.model.bottomSheetItems.BottomSheetItems
import com.jasmeet.wallcraft.model.database.DownloadsDatabase
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.LoaderView
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.loadImageFromUrl
import com.jasmeet.wallcraft.view.setWallpaper2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    data: String?,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    id: String?
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    var downloadedImages by remember {
        mutableStateOf<MutableList<DownloadEntity>>(mutableListOf())
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    val iconState = remember { mutableIntStateOf(R.drawable.ic_download) }
    val downloadDao = DownloadsDatabase.getInstance(context).downloadDao()

    var showToolTip by remember { mutableStateOf(false) }

    val bottomSheetItems = listOf(
        BottomSheetItems(
            title = "Set as Lock Screen Wallpaper",
            onClick = {
                setWallpaperAndHandleLoading(
                    coroutine,
                    context,
                    data,
                    WallpaperType.LOCK_SCREEN,
                    isLoading
                )
            }
        ),
        BottomSheetItems(
            title = "Set as Home Screen Wallpaper",
            onClick = {
                setWallpaperAndHandleLoading(
                    coroutine,
                    context,
                    data,
                    WallpaperType.HOME_SCREEN,
                    isLoading
                )
            }
        ),
        BottomSheetItems(
            title = "Set as Both Screen Wallpaper",
            onClick = {
                setWallpaperAndHandleLoading(
                    coroutine,
                    context,
                    data,
                    WallpaperType.BOTH,
                    isLoading
                )
            }
        ),
    )

    LaunchedEffect(true) {
        downloadedImages = downloadDao.getAllDownloads().toMutableList()
    }

    LaunchedEffect(downloadedImages) {
        val isDownloaded = downloadedImages.any { it.url == data }
        iconState.intValue = if (isDownloaded) R.drawable.ic_downloaded else R.drawable.ic_download
    }

    BackHandler {
        onBackClick.invoke()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                title = {},
                navigationIcon = {
                    IconTonalButtonComponent(
                        icon = R.drawable.ic_back,
                        modifier = Modifier
                            .padding(end = 8.dp),
                        onClick = onBackClick
                    )
                },
                actions = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AnimatedVisibility(visible = showToolTip && id != null) {
                            Text(text = id.toString())
                        }


                        IconTonalButtonComponent(
                            icon = R.drawable.ic_info,
                            onClick = {
                                if (id != null) {
                                    coroutine.launch {
                                        showToolTip = true
                                        delay(2000)
                                        showToolTip = false
                                    }
                                }
                            }
                        )

                    }

                }
            )
        }
    ) { _ ->
        Box {
            data?.let { imgUrl ->
                NetworkImage(
                    url = imgUrl,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .blur(16.dp)
                        .fillMaxSize()
                )
            }
            Box(
                Modifier
                    .align(Alignment.Center)
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.75f)
            ) {
                data?.let { imgUrl ->
                    NetworkImage(
                        url = imgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image-$data"),
                                animatedVisibilityScope,
                            )

                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                }
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
                        icon = iconState.intValue,
                        onClick = {
                            coroutine.launch {
                                if (!downloadedImages.any { it.url == data }) {
                                    withContext(Dispatchers.IO) {
                                        downloadFile(data.toString(), context)
                                        downloadedImages =
                                            downloadDao.getAllDownloads().toMutableList()
                                        iconState.intValue = R.drawable.ic_downloaded
                                    }

                                }
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                }

            }



            Button(
                onClick = {
                    isSheetOpen = true
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0x66725FFE)
                )
            ) {
                TextComponent(
                    text = "Apply",
                    textSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textColor = Color.White
                )

            }
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    isSheetOpen = false
                    coroutine.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState,
                scrimColor = Color.Transparent,
                shape = MaterialTheme.shapes.medium,
                windowInsets = WindowInsets(left = 10.dp, right = 10.dp, bottom = 45.dp),
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .offset(y = (-8).dp)
                ) {
                    items(bottomSheetItems.size) { index ->
                        TextComponent(
                            text = bottomSheetItems[index].title,
                            modifier = Modifier
                                .clickable {
                                    bottomSheetItems[index].onClick.invoke()
                                    isSheetOpen = false
                                    coroutine.launch {
                                        sheetState.hide()
                                    }
                                }
                                .fillMaxWidth()
                                .padding(vertical = 5.dp, horizontal = 12.dp),
                            textSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (index < bottomSheetItems.size - 1) {
                            HorizontalDivider()
                        }
                    }
                    item { Spacer(modifier = Modifier.height(2.dp)) }
                }

            }
        }
        if (isLoading.value) {
            LoaderView()
        }

    }


}


private fun setWallpaperAndHandleLoading(
    coroutine: CoroutineScope,
    context: Context,
    data: String?,
    wallpaperType: WallpaperType,
    isLoading: MutableState<Boolean>
) {
    isLoading.value = true
    coroutine.launch {
        val bitmap = withContext(Dispatchers.IO) {
            data?.let { loadImageFromUrl(it) }
        }
        setWallpaper2(bitmap, context, wallpaperType)
        isLoading.value = false
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
        .setDestinationUri(Uri.fromFile(file)) // Saving downloaded file to the specified destination

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


