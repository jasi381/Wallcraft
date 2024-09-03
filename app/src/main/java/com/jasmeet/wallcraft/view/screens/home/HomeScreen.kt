package com.jasmeet.wallcraft.view.screens.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.view.appComponents.NoInternetView
import com.jasmeet.wallcraft.view.appComponents.OrderByButton
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SharedTransitionScope.HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onImageClicked: (Triple<String, String, String>) -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val activity = (LocalContext.current as? Activity)
    val data = homeViewModel.homeData.collectAsLazyPagingItems()
    val error = homeViewModel.error.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val randomImgData = homeViewModel.randomImgData.collectAsState(null)

    val lazyListState = rememberLazyListState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }
    var isHolding by remember { mutableStateOf(false) }

    var showInfoDialog by rememberSaveable { mutableStateOf(false) }
    var showInfoRandomImgData by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        if (selectedIndex.intValue == 1) {
            selectedIndex.intValue = 0
            homeViewModel.updateOrderBy(OrderBy.LATEST.displayName)
        } else {
            activity?.finish()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showInfoRandomImgData = true
                            homeViewModel.getRandomImage()
                        }) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { paddingValues ->
        if (error.value?.isNotEmpty() == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NoInternetView(error = error.value ?: "Something went wrong!")
            }
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        OrderByButton(
                            isSelected = selectedIndex.intValue == 0,
                            onClick = {
                                selectedIndex.intValue = 0
                                homeViewModel.updateOrderBy(OrderBy.LATEST.displayName)
                            },
                            text = "Latest"
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        OrderByButton(
                            isSelected = selectedIndex.intValue == 1,
                            onClick = {
                                selectedIndex.intValue = 1
                                homeViewModel.updateOrderBy(OrderBy.POPULAR.displayName)
                            },
                            text = "Popular"
                        )
                    }
                }


                items(
                    data.itemCount / 2,
                    key = {
                        it.toString()
                    }) { rowIndex ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (0..1).forEach { columnIndex ->
                            val index = rowIndex * 2 + columnIndex
                            if (index < data.itemCount) {
                                val item = data[index]

                                if (item != null) {


                                    val encodedUrl =
                                        URLEncoder.encode(item.urls.regular, "UTF-8")
                                    val encodedLowQuality =
                                        URLEncoder.encode(item.urls.small, "UTF-8")

                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(item.urls.full.toString())
                                            .crossfade(true)
                                            .build(),
                                        placeholder = painterResource(id = R.drawable.img_placeholder),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = item.altDescription,
                                        modifier = Modifier
                                            .sharedElement(
                                                state = rememberSharedContentState(
                                                    key = "image-${item.urls.regular}"
                                                ),
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                boundsTransform = { _, _ ->
                                                    tween(
                                                        durationMillis = 800,
                                                        easing = CubicBezierEasing(
                                                            0.5f,
                                                            0.75f,
                                                            0.1f,
                                                            0.85f
                                                        )
                                                    )

                                                }
                                            )
                                            .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
                                            .clip(MaterialTheme.shapes.large)
                                            .pointerInput(Unit) {
                                                awaitEachGesture {
                                                    val down =
                                                        awaitFirstDown(requireUnconsumed = false)
                                                    val downTime = System.currentTimeMillis()

                                                    var holdJob: Job? = null
                                                    holdJob = scope.launch {
                                                        delay(300) // Adjust this delay as needed
                                                        isHolding = true
                                                        selectedImageUrl = item.urls.regular
                                                        showInfoDialog = true
                                                        Log.d("Gesture", "Hold started")
                                                    }

                                                    val up = waitForUpOrCancellation()
                                                    holdJob.cancel()

                                                    when (up) {
                                                        null -> {
                                                            // The gesture was cancelled
                                                            isHolding = false
                                                            showInfoDialog = false
                                                            Log.d("Gesture", "Gesture cancelled")
                                                        }

                                                        else -> {
                                                            val upTime = System.currentTimeMillis()
                                                            if (isHolding) {
                                                                // Release after hold
                                                                isHolding = false
                                                                showInfoDialog = false
                                                                Log.d("Gesture", "Hold released")

                                                            } else if (upTime - downTime < 300) {
                                                                // This was a quick tap
                                                                Log.d("Gesture", "Item clicked")
                                                                onImageClicked(
                                                                    Triple(
                                                                        encodedUrl,
                                                                        item.id.toString(),
                                                                        encodedLowQuality
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            .weight(1f)
                                    )
                                }
                                else {
                                    // Render shimmer effect for loading state
                                    ShimmerItem(
                                        modifier = Modifier
                                            .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
                                            .weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        data.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(paddingValues)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 50.dp)
                                .navigationBarsPadding(),
                            color = MaterialTheme.colorScheme.onBackground,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(paddingValues)
                                .padding(bottom = 50.dp)
                                .align(Alignment.BottomCenter)
                                .navigationBarsPadding(),
                            color = MaterialTheme.colorScheme.onBackground,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }
    if (showInfoDialog && selectedImageUrl != null) {
        Dialog(onDismissRequest = { showInfoDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { showInfoDialog = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Dialog")
                }

                AsyncImage(
                    model = selectedImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    if (showInfoRandomImgData && randomImgData.value != null) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                ) {

                    val encodedUrl =
                        URLEncoder.encode(randomImgData.value?.urls?.regular, "UTF-8")
                    val encodedLowQuality =
                        URLEncoder.encode(randomImgData.value?.urls?.small, "UTF-8")

                    AsyncImage(
                        model = randomImgData.value?.urls?.regular,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            onImageClicked(
                                Triple(
                                    encodedUrl,
                                    randomImgData.value?.id.toString(),
                                    encodedLowQuality
                                )
                            )
                            showInfoRandomImgData = false
                        }) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = "More Info")
                        }

                        IconButton(onClick = { showInfoRandomImgData = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Dialog"
                            )
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun ShimmerItem(modifier: Modifier = Modifier) {
    ShimmerEffect(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
    )
}

@Composable
fun ShimmerEffect(
    modifier: Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) {


    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 1.0f),
        Color.White.copy(alpha = 0.5f),
        Color.White.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )

    Box(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )
    }


}






