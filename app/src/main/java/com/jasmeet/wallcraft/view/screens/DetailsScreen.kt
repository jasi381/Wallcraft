package com.jasmeet.wallcraft.view.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.commandiron.compose_loading.Circle
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.WallpaperType
import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.view.appComponents.BottomSheetComponent
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.DetailsViewModel
import com.jasmeet.wallcraft.viewModel.DownloadViewModel
import com.jasmeet.wallcraft.viewModel.WallpaperViewModel
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrColors
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.brush
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    data: String?,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    id: String?,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    wallpaperViewModel: WallpaperViewModel = hiltViewModel(),
    downloadViewModel: DownloadViewModel = hiltViewModel(),
    onProfileImageClick: () -> Unit = {}
) {

    val details = detailsViewModel.details.collectAsState()
    val isWallpaperLoading = wallpaperViewModel.isLoading.collectAsState()
    val isDownloading = downloadViewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = Unit) {
        id?.let { detailsViewModel.getDetails(it) }
    }

    val coroutine = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val showWallpaperTypeSheet = rememberSaveable { mutableStateOf(false) }
    val showDownloadQualitySheet = rememberSaveable { mutableStateOf(false) }
    var showQrCode by rememberSaveable { mutableStateOf(false) }
    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}


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
                                .clickable {
                                    onProfileImageClick.invoke()
                                }
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
                        icon = R.drawable.ic_download,
                        onClick = {
                            showDownloadQualitySheet.value = true
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    IconTonalButtonComponent(
                        icon = R.drawable.ic_share,
                        onClick = { detailsViewModel.shareImage(data, shareLauncher) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    IconTonalButtonComponent(
                        icon = R.drawable.ic_qr_code,
                        onClick = { showQrCode = true }
                    )
                }

            }

            Button(
                onClick = {
                    showWallpaperTypeSheet.value = true
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
        if (showWallpaperTypeSheet.value) {
            WallpaperTypeBottomSheet(
                showWallpaperTypeSheet,
                coroutine,
                sheetState,
                isWallpaperLoading,
                wallpaperViewModel,
                data
            )
        }
        if (showDownloadQualitySheet.value) {
            ShowDownloadQualityBottomSheet(
                showDownloadQualitySheet,
                coroutine,
                sheetState,
                details,
                downloadViewModel,
                isDownloading
            )
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

                    val qrcodePainter = rememberQrCodePainter(
                        data = data.toString(),
                        shapes = QrShapes(
                            darkPixel = QrPixelShape.circle(),
                            frame = QrFrameShape.circle()
                        ),
                        colors = QrColors(
                            dark = QrBrush.brush {
                                Brush.linearGradient(
                                    0f to Color.Red,
                                    1f to Color.Blue,
                                    end = Offset(it, it)
                                )
                            }
                        )

                    )
                    Image(
                        painter = qrcodePainter,
                        contentDescription = "QR code referring to the example.com website",
                        modifier = Modifier.padding(10.dp)
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDownloadQualityBottomSheet(
    showDownloadQualitySheet: MutableState<Boolean>,
    coroutine: CoroutineScope,
    sheetState: SheetState,
    details: State<DetailsApiResponse?>,
    downloadViewModel: DownloadViewModel,
    isDownloading: State<Boolean>
) {
    BottomSheetComponent(
        onDismiss = {
            showDownloadQualitySheet.value = false
            coroutine.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        AnimatedVisibility(
            visible = !isDownloading.value,
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
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_download),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(40.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                TextComponent(
                    text = stringResource(id = R.string.select_download_type),
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
                        details.value?.urls?.raw?.let { downloadViewModel.startDownload(it) }
                        showDownloadQualitySheet.value = false
                        coroutine.launch {
                            sheetState.hide()
                        }
                    },
                    loading = false,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "Raw (Very High Quality)",
                    textSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoadingButton(
                    onClick = {
                        details.value?.urls?.full?.let { downloadViewModel.startDownload(it) }
                        showDownloadQualitySheet.value = false
                        coroutine.launch {
                            sheetState.hide()
                        }
                    },
                    loading = false,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "Full (High Quality)",
                    textSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoadingButton(
                    onClick = {
                        details.value?.urls?.regular?.let { downloadViewModel.startDownload(it) }
                        showDownloadQualitySheet.value = false
                        coroutine.launch {
                            sheetState.hide()
                        }
                    },
                    loading = false,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "Medium (Medium Quality)",
                    textSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                LoadingButton(
                    onClick = {
                        details.value?.urls?.small?.let { downloadViewModel.startDownload(it) }
                        showDownloadQualitySheet.value = false
                        coroutine.launch {
                            sheetState.hide()
                        }
                    },
                    loading = false,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    text = "Low (Low Quality)",
                    textSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }

        AnimatedVisibility(
            visible = isDownloading.value,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WallpaperTypeBottomSheet(
    showWallpaperTypeSheet: MutableState<Boolean>,
    coroutine: CoroutineScope,
    sheetState: SheetState,
    isWallpaperLoading: State<Boolean>,
    wallpaperViewModel: WallpaperViewModel,
    data: String?
) {
    BottomSheetComponent(
        onDismiss = {
            showWallpaperTypeSheet.value = false
            coroutine.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        AnimatedVisibility(
            visible = !isWallpaperLoading.value,
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

                        wallpaperViewModel.setWallpaperAndHandleLoading(
                            data.toString(),
                            WallpaperType.LOCK_SCREEN
                        )
                        showWallpaperTypeSheet.value = false
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
                        wallpaperViewModel.setWallpaperAndHandleLoading(
                            data.toString(),
                            WallpaperType.HOME_SCREEN
                        )
                        showWallpaperTypeSheet.value = false
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
                        wallpaperViewModel.setWallpaperAndHandleLoading(
                            data.toString(),
                            WallpaperType.BOTH
                        )
                        showWallpaperTypeSheet.value = false
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
            visible = isWallpaperLoading.value,
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





