package com.jasmeet.wallcraft.view.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
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
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.CategoryDetailsViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.CategoryDetailsScreen(
    name: String?,
    onBackClick: () -> Boolean,
    animatedVisibilityScope: AnimatedContentScope,
    categoryDetailsViewModel: CategoryDetailsViewModel = hiltViewModel(),
    onImageClicked: (Triple<String, String, String>) -> Unit,
) {
    BackHandler {
        onBackClick.invoke()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val response = categoryDetailsViewModel.details.collectAsState()
    val error = categoryDetailsViewModel.error.collectAsState()
    val isLoading = categoryDetailsViewModel.loading.collectAsState()

    var selectedPage by remember { mutableIntStateOf(1) }
    val gridState = rememberLazyStaggeredGridState()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = selectedPage) {
        name?.let { categoryDetailsViewModel.getCategoryDetails(it, selectedPage) }
    }

    val shouldLoadMore by remember {
        derivedStateOf {
            val visibleItems = gridState.layoutInfo.visibleItemsInfo
            val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
            val totalItems = response.value?.results?.size ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !isLoading.value) {
            selectedPage++
            categoryDetailsViewModel.getCategoryDetails(name.orEmpty(), selectedPage)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    name?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                },
                navigationIcon = {
                    IconTonalButtonComponent(
                        icon = R.drawable.ic_back,
                        modifier = Modifier
                            .padding(end = 8.dp),
                        onClick = {
                            onBackClick.invoke()
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    Box(Modifier.height(LocalConfiguration.current.screenHeightDp.dp)) {
                        LazyVerticalStaggeredGrid(
                            state = gridState,
                            columns = StaggeredGridCells.Adaptive(150.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalItemSpacing = 12.dp,
                        ) {
                            items(
                                count = response.value?.results?.size ?: 0,
                                key = {
                                    it.toString()
                                }
                            ) { index ->

                                val scale = remember { Animatable(0.85f) }
                                val alpha = remember { Animatable(0.5f) }

                                LaunchedEffect(key1 = true) {
                                    launch {
                                        scale.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                    launch {
                                        alpha.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = LinearEasing
                                            )
                                        )
                                    }
                                }


                                val data = response.value?.results?.get(index)
                                val encodedUrl = URLEncoder.encode(data?.urls?.regular, "UTF-8")
                                val lowEncodedUrl = URLEncoder.encode(data?.urls?.small, "UTF-8")
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(data?.urls?.regular.toString())
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.img_placeholder),
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = data?.altDescription,
                                    modifier = Modifier
                                        .sharedElement(
                                            state = rememberSharedContentState(
                                                key = "image-${data?.urls?.regular}"
                                            ),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                        .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
                                        .clip(MaterialTheme.shapes.large)
                                        .clickable {
                                            onImageClicked(
                                                Triple(
                                                    encodedUrl,
                                                    data?.id.toString(),
                                                    lowEncodedUrl
                                                )
                                            )
                                        }
                                        .graphicsLayer {
                                            this.scaleX = scale.value
                                            this.scaleY = scale.value
                                            this.alpha = alpha.value
                                        }
                                )
                            }
                        }
                    }
                }

                if (isLoading.value) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onBackground,
                    strokeCap = StrokeCap.Round
                )
            }
        }

    }
}

