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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.jasmeet.wallcraft.view.appComponents.LottieComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.FavouritesViewModel
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.FavouritesScreen(
    animatedVisibilityScope: AnimatedContentScope,
    onBackClick: () -> Unit,
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    onImageClicked: (Triple<String, String, String>) -> Unit
) {

    LaunchedEffect(key1 = true) {
        favouritesViewModel.getAllPhotos()

    }

    val data = favouritesViewModel.favouritePhotos.observeAsState()
    val context = LocalContext.current
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Create an animated offset
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = Ease),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favourites",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "favourites"),
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
                scrollBehavior = scrollBehaviour,
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
    ) {
        AnimatedVisibility(
            visible = data.value.isNullOrEmpty(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Box(Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    LottieComponent(rawRes = R.raw.empty, modifier = Modifier)
                    Text(
                        text = "No Favourites ",
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

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(it),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 12.dp,
        ) {

            items(
                count = data.value.orEmpty().size,
                key = {keys->
                    keys.toString()
                }
            ) { index ->
                val animatable = remember {
                    Animatable(0.85f)
                }

                LaunchedEffect(key1 = true) {
                    animatable.animateTo(1f, tween(350, delayMillis = 100, easing = LinearEasing))

                }

                val encodedUrl =
                    URLEncoder.encode(data.value?.get(index)?.photoUrl, "UTF-8")

                val encodedLowQuality =
                    URLEncoder.encode(data.value?.get(index)?.lowQualityUrl, "UTF-8")

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(data.value?.get(index)?.photoData)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.img_placeholder),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = "image-${data.value?.get(index)?.photoUrl}"
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        }
                        .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            onImageClicked(
                                Triple(
                                    encodedUrl,
                                    data.value?.get(index)?.id.toString(),
                                    encodedLowQuality
                                )
                            )
                        }
                )
            }
        }
    }
}