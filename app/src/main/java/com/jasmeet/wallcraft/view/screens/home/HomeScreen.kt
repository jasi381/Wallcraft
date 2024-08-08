package com.jasmeet.wallcraft.view.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
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

    val lazyListState = rememberLazyListState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
                        onClick = { }) {
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
                item {
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


                                val encodedUrl =
                                    URLEncoder.encode(item?.urls?.regular, "UTF-8")
                                val encodedLowQuality =
                                    URLEncoder.encode(item?.urls?.small, "UTF-8")

                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(item?.urls?.full.toString())
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.img_placeholder),
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = item?.altDescription,
                                    modifier = Modifier
                                        .sharedElement(
                                            state = rememberSharedContentState(
                                                key = "image-${item?.urls?.regular}"
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
                                        .clickable {
                                            onImageClicked(
                                                Triple(
                                                    encodedUrl,
                                                    item?.id.toString(),
                                                    encodedLowQuality
                                                )
                                            )
                                        }
                                        .weight(1f)
                                )
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
}







