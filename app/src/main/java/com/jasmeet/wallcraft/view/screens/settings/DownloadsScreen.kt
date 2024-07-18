package com.jasmeet.wallcraft.view.screens.settings

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity
import com.jasmeet.wallcraft.utils.Utils.groupItemsByDate
import com.jasmeet.wallcraft.view.appComponents.LottieComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.DownloadViewModel
import java.net.URLEncoder

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SharedTransitionScope.DownloadsScreen(
    animatedVisibilityScope: AnimatedContentScope,
    onBackClick: () -> Unit,
    downloadViewModel: DownloadViewModel = hiltViewModel(),
    onImageClicked: (Triple<String, String, String>) -> Unit
) {
    LaunchedEffect(key1 = true) {
        downloadViewModel.getAllPhotos()
    }
    val data = downloadViewModel.downloadedPhoto.observeAsState()

    // Filter out items with null photoUrl
    val filteredData = data.value?.filter { it.photoUrl.isNotBlank() } ?: emptyList()

    // Group items by date
    val groupedData = groupItemsByDate(filteredData)

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = Ease),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Downloads",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "downloads"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(500)
                            }
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = filteredData.isEmpty(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Box(Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    LottieComponent(rawRes = R.raw.empty, modifier = Modifier)
                    Text(
                        text = "No Downloads",
                        modifier = Modifier
                            .offset(y = (-150).dp)
                            .scale(scale)
                            .align(Alignment.CenterHorizontally),
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            groupedData.forEach { (date, items) ->
                stickyHeader {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp)
                    )
                }

                items(items.chunked(3)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            PhotoItem(
                                item = item,
                                modifier = Modifier
                                    .sharedElement(
                                        state = rememberSharedContentState(
                                            key = "image-${item.photoUrl}"
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                                    .weight(1f),
                                onImageClicked = onImageClicked
                            )
                        }

                        // Add empty boxes if the row is not full
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PhotoItem(
    item: DownloadsEntity,
    modifier: Modifier = Modifier,
    onImageClicked: (Triple<String, String, String>) -> Unit
) {
    val animatable = remember { Animatable(0.85f) }

    LaunchedEffect(key1 = true) {
        animatable.animateTo(1f, tween(350, delayMillis = 100, easing = LinearEasing))
    }

    val encodedUrl = URLEncoder.encode(item.photoUrl, "UTF-8")
    val encodedLowQuality = URLEncoder.encode(item.lowResPhotoUrl, "UTF-8")

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(item.photoUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(id = R.drawable.img_placeholder),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .graphicsLayer {
                scaleX = animatable.value
                scaleY = animatable.value
            }
            .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 7f)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onImageClicked(
                    Triple(
                        encodedUrl,
                        item.id,
                        encodedLowQuality
                    )
                )
            }
    )
}
