package com.jasmeet.wallcraft.view.screens

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jasmeet.wallcraft.viewModel.HomeViewModel
import org.jetbrains.annotations.Async

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onImageClicked: (Pair<String, String>) -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
) {


    val data = homeViewModel.data.collectAsLazyPagingItems()
//    val error = homeViewModel.error.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            onClick = {
                Log.d("Response", data.toString())
            },
            modifier = Modifier
                .align(Alignment.Start)
                .statusBarsPadding()
        ) {
            Text(text = "Load More")
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)) {
            items(data.itemCount){index ->
                AsyncImage(model = data[index]?.regular, contentDescription = null, modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp, 190.dp)
                    .clickable {
                    }, contentScale = ContentScale.FillBounds)
                
                
            }

        }

    }



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

//
//    if (error.value?.isNotEmpty() == true) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            NoInternetView()
//        }
//    } else {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//
//        ) {
//            LazyVerticalStaggeredGrid(
//                columns = StaggeredGridCells.Adaptive(150.dp),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(10.dp),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                verticalItemSpacing = 12.dp
//            ) {
//                items(
//                    count = data.itemCount,
//                    key = {
//                        data[it]?.id ?: it
//                    }
//                ) { index ->
//
//                    val encodedUrl = URLEncoder.encode(data[index]?.src?.portrait, "UTF-8")
//
//                    NetworkImage(
//                        url = data[index]?.src?.portrait.toString(),
//                        enableCrossFade = true,
//                        modifier = Modifier
//                            .sharedElement(
//                                state = rememberSharedContentState(
//                                    key = "image-${data[index]?.src?.portrait}"
//                                ),
//                                animatedVisibilityScope = animatedVisibilityScope,
//                            )
//                            .height(LocalConfiguration.current.screenWidthDp.dp * 2 / 3f)
//                            .clip(MaterialTheme.shapes.large)
//                            .clickable {
//                                onImageClicked(
//                                    Pair(
//                                        encodedUrl,
//                                        data[index]?.photographer.toString()
//                                    )
//                                )
//                            }
//
//                    )
//                }
//            }
//
//            data.apply {
//                when {
//                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .padding(bottom = 90.dp)
//                                .align(Alignment.BottomCenter)
//                                .navigationBarsPadding(),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            strokeCap = StrokeCap.Round
//                        )
//                    }
//
//                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
//
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .align(Alignment.BottomCenter)
//                                .navigationBarsPadding(),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            strokeCap = StrokeCap.Round
//                        )
//                    }
//
//                }
//            }
//        }
//    }


}







