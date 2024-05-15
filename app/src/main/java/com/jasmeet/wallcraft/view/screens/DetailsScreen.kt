package com.jasmeet.wallcraft.view.screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.compose_loading.Circle
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity
import com.jasmeet.wallcraft.model.database.DownloadsDatabase
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.BottomSheetComponent
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.loadImageFromUrl
import com.jasmeet.wallcraft.view.setWallpaper2
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.DetailsViewModel
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    data: String?,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    id: String?,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val details = detailsViewModel.details.collectAsState()

    LaunchedEffect(key1 = Unit) {
        id?.let { detailsViewModel.getDetails(it) }
    }


    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    var downloadedImages by remember { mutableStateOf<List<DownloadEntity>>(emptyList()) }
    val sheetState = rememberModalBottomSheetState()
    var showWallpaperTypeSheet by rememberSaveable { mutableStateOf(false) }
    var showQrCode by rememberSaveable { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }

    val iconState = remember { mutableIntStateOf(R.drawable.ic_download) }
    val downloadDao = DownloadsDatabase.getInstance(context).downloadDao()

    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }



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

                    details.value?.user?.profileImage?.large?.let {
                        NetworkImage(
                            url = it,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {}
                                .size(45.dp)

                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

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

                    IconTonalButtonComponent(
                        icon = R.drawable.ic_share,
                        onClick = {
                            coroutine.launch(Dispatchers.IO) {
                                bitmap.value = data?.let { Utils.getBitmapFromUrl(it) }
                                byteArray.value = bitmap.value.let {
                                    it?.let { it1 ->
                                        Utils.bitmapToByteArray(
                                            it1
                                        )
                                    }
                                }

                                val imagesDir =
                                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                val imageFile = File(imagesDir, "shared_image.jpg")
                                FileOutputStream(imageFile).use { outputStream ->
                                    bitmap.value?.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        75,
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
                                        type = "image/jpeg"
                                        putExtra(Intent.EXTRA_STREAM, uri)
                                    }
                                    val chooser = Intent.createChooser(shareIntent, "Share Image")
                                    Log.d("ShareImage", "Launching share intent")
                                    shareLauncher.launch(chooser)
                                }
                            }
                        }
                    )

                }

            }

            Button(
                onClick = {
                    showWallpaperTypeSheet = true
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
        if (showWallpaperTypeSheet) {
            BottomSheetComponent(
                onDismiss = {
                    showWallpaperTypeSheet = false
                    coroutine.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                AnimatedVisibility(
                    visible = !isLoading.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.wallpaper),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .size(40.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        TextComponent(
                            text = stringResource(id = R.string.select_wallpaper_type),
                            fontFamily = poppins,
                            modifier = Modifier.padding(bottom = 16.dp, start = 10.dp, end = 10.dp),
                            textAlign = TextAlign.Center,
                            maxLines = Int.MAX_VALUE,
                            textSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LoadingButton(
                            onClick = {
                                setWallpaperAndHandleLoading(
                                    coroutine,
                                    context,
                                    data,
                                    WallpaperType.LOCK_SCREEN,
                                    isLoading
                                )
                                showWallpaperTypeSheet = false
                                coroutine.launch {
                                    sheetState.hide()
                                }
                            },
                            loading = false,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth(),
                            text = "Set as Lock Screen Wallpaper",
                            textSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LoadingButton(
                            onClick = {
                                setWallpaperAndHandleLoading(
                                    coroutine,
                                    context,
                                    data,
                                    WallpaperType.HOME_SCREEN,
                                    isLoading
                                )
                                showWallpaperTypeSheet = false
                                coroutine.launch {
                                    sheetState.hide()
                                }
                            },
                            loading = false,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth(),
                            text = "Set as Home Screen Wallpaper",
                            textSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LoadingButton(
                            onClick = {
                                setWallpaperAndHandleLoading(
                                    coroutine,
                                    context,
                                    data,
                                    WallpaperType.BOTH,
                                    isLoading
                                )
                                showWallpaperTypeSheet = false
                                coroutine.launch {
                                    sheetState.hide()
                                }
                            },
                            loading = false,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth(),
                            text = "Set as Lock & Home Screen Wallpaper",
                            textSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }

                AnimatedVisibility(
                    visible = isLoading.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Circle(
                            modifier = Modifier.padding(vertical = 15.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                }
            }
        }
        if (showQrCode) {
            Dialog(onDismissRequest = {
                showQrCode = false
            }) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.White, MaterialTheme.shapes.extraLarge),
                    contentAlignment = Alignment.Center
                ) {
                    val qrcodePainter = data?.let {
                        rememberQrCodePainter(
                            data = it,
                            shapes = QrShapes(
                                darkPixel = QrPixelShape.roundCorners()
                            )
                        )
                    }
                    qrcodePainter?.let {
                        Image(
                            painter = it,
                            contentDescription = "QR code referring to the example.com website"
                        )
                    }

                }
            }
        }
    }
}

fun shareImage(
    data: String?,
    shareImageLauncher: ActivityResultLauncher<Intent>,
    context: Context
) {


    val bitmap = data?.let { Utils.getBitmapFromUrl(it) }
    val byteArray = bitmap?.let { Utils.bitmapToByteArray(it) }

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/jpeg"
        putExtra(Intent.EXTRA_STREAM, byteArray)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    shareImageLauncher.launch(Intent.createChooser(shareIntent, "Share Image"))

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
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            appName
        )
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


//@Composable
//private fun rememberShareImageLauncher(): ActivityResultLauncher<Intent> {
//    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        // Handle the result if needed
//    }
//}


