package com.jasmeet.wallcraft.view.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.NoInternetView
import com.jasmeet.wallcraft.viewModel.HomeViewModel
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onImageClicked: (Pair<String, String>) -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
) {


    val data = homeViewModel.homeData.collectAsLazyPagingItems()
    val error = homeViewModel.error.collectAsState()
    val context = LocalContext.current

//    Column(Modifier.fillMaxSize()) {
//        Button(
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Blue,
//                contentColor = Color.White
//            ),
//            onClick = {
//                Log.d("Response", data.toString())
//            },
//            modifier = Modifier
//                .align(Alignment.Start)
//                .statusBarsPadding()
//        ) {
//            Text(text = "Load More")
//        }
//
//        LazyColumn(
//            Modifier
//                .fillMaxSize()
//                .weight(1f)) {
//            items(data.itemCount){index ->
//                AsyncImage(
//                    model = data[index]?.urls?.full,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .size(100.dp, 190.dp)
//                        .clickable {
//                                   Log.d("Id", data[index]?.id.toString())
//                            Log.d("Id-Desc", data[index]?.altDescription.toString())
//                        },
//                    contentScale = ContentScale.FillBounds,
//                    placeholder = painterResource(id = R.drawable.img_placeholder)
//                )
//
//
//            }
//
//        }
//
//    }



    //TODO: Hide the bottom navigation bar on scroll Down and show it on scroll Up & None

//    val lazyListState = rememberLazyStaggeredGridState()
//    var lastScrollPosition by remember { mutableStateOf(0) }
//    var scrollDirection by remember { mutableStateOf(ScrollDirection.None) }
//
//    LaunchedEffect(lazyListState) {
//        snapshotFlow { lazyListState.firstVisibleItemIndex }
//            .collect { currentScrollPosition ->
//                scrollDirection = when {
//                    currentScrollPosition > lastScrollPosition -> ScrollDirection.Down
//                    currentScrollPosition < lastScrollPosition -> ScrollDirection.Up
//                    else -> ScrollDirection.None
//                }
//                lastScrollPosition = currentScrollPosition
//            }
//    }


    if (error.value?.isNotEmpty() == true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NoInternetView()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()

        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 12.dp
            ) {
                items(
                    count = data.itemCount,
                    key = {
                        it.toString()
                    }
                ) { index ->

                    val encodedUrl = URLEncoder.encode(data[index]?.urls?.regular, "UTF-8")

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data[index]?.urls?.full.toString())
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.img_placeholder),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = data[index]?.altDescription,
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(
                                    key = "image-${data[index]?.urls?.regular}"
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                            .height(LocalConfiguration.current.screenWidthDp.dp * 2 / 3f)
                            .clip(MaterialTheme.shapes.large)
                            .clickable {
                                onImageClicked(
                                    Pair(encodedUrl, data[index]?.id.toString())
                                )
                            }

                    )
                }
            }

            data.apply {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(bottom = 90.dp)
                                .align(Alignment.BottomCenter)
                                .navigationBarsPadding(),
                            color = MaterialTheme.colorScheme.onBackground,
                            strokeCap = StrokeCap.Round
                        )
                    }

                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {

                        CircularProgressIndicator(
                            modifier = Modifier
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







