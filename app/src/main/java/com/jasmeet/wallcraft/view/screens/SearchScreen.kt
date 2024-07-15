package com.jasmeet.wallcraft.view.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.SearchBar
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.viewModel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchScreen(

    animatedVisibilityScope: AnimatedContentScope,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onImageClicked: (Pair<String, String>) -> Unit,
) {

    val response = searchViewModel.details.collectAsState()
    val error = searchViewModel.error.collectAsState()
    val isLoading = searchViewModel.loading.collectAsState()
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var selectedPage by remember { mutableIntStateOf(1) }
    val gridState = rememberLazyStaggeredGridState()
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


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
            searchViewModel.getSearchResults(searchText, selectedPage)

        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .focusRequester(focusRequester),
                onClear = {
                    searchText = ""
                    searchViewModel.clearSearchResults()
                    keyboardController?.show()
                    focusRequester.requestFocus()

                },
                labelValue = "Enter your query...",
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotEmpty()) {
                            searchViewModel.getSearchResults(searchText, selectedPage)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            focusManager.clearFocus()
                        }
                    }
                )

            )
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                enabled = searchText.isNotEmpty(),
                onClick = {
                    searchViewModel.getSearchResults(searchText, selectedPage)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    focusManager.clearFocus()
                }) {
                TextComponent(text = "Go", textSize = 18.sp)
            }
        }
        Box(

            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize()


        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                if (response.value?.results?.isNotEmpty() == true) {
                    item {
                        Box(Modifier.height(LocalConfiguration.current.screenHeightDp.dp)) {
                            LazyVerticalStaggeredGrid(
                                state = gridState,
                                columns = StaggeredGridCells.Adaptive(150.dp),
                                modifier = Modifier
                                    .navigationBarsPadding()
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
                                    val data = response.value?.results?.get(index)
                                    val encodedUrl =
                                        URLEncoder.encode(data?.urls?.regular, "UTF-8")
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
                                                    Pair(encodedUrl, data?.id.toString())
                                                )
                                            }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .imePadding(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(300.dp)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                MultipleCircularRippleEffect()
                            }

                            Spacer(modifier = Modifier.height(70.dp))

                            Text(text = "Search your query")

                            Spacer(modifier = Modifier.weight(1f))
                        }

//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Column(
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    MultipleCircularRippleEffect()
//                                    Spacer(modifier = Modifier.height(10.dp))
//                                    TextComponent(text = "Search  your query")
//                                }
//                            }
                    }
                }
                if (error.value?.isNotEmpty() == true) {
                    item {
                        TextComponent(text = error.value.toString())
                    }
                }


            }
            if (isLoading.value) {
                Box(
                    Modifier
                        .navigationBarsPadding()
                        .fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }


            }
        }
    }
}


//            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                items(
//                    searchResponse.itemCount,
//                    key = {
//                        it.toString()
//                    }
//                ) { index ->
//
//                    searchResponse[index]?.src?.portrait?.let {
//                        NetworkImage(
//                            url = it, modifier = Modifier
//                                .clip(MaterialTheme.shapes.extraLarge)
//                                .size(70.dp)
//                        )
//                    }
//
//                }
//
//            }
//    val context = LocalContext.current
//    var downloadedImages by remember {
//        mutableStateOf<MutableList<DownloadEntity>>(mutableListOf())
//    }
//
//    LaunchedEffect(true) {
//
//        val downloadDao = DownloadsDatabase.getInstance(context).downloadDao()
//        downloadedImages = downloadDao.getAllDownloads().toMutableList()
//
//    }
//
//    LazyColumn {
//        items(downloadedImages.size) {
//            val convertByteArrayToBitmap = downloadedImages[it].imageBytes?.let { it1 ->
//                Utils.byteArrayToBitmap(
//                    it1
//                )
//            }
//            TextComponent(text = downloadedImages[it].url)
//            convertByteArrayToBitmap?.let { it1 ->
//                Image(
//                    bitmap = it1.asImageBitmap(),
//                    contentDescription = null
//                )
//            }
//        }
//    }


@Composable
fun MultipleCircularRippleEffect() {
    var rippleCenter by remember { mutableStateOf(Offset.Zero) }
    val rippleCount = 4
    val ripples = remember {
        List(rippleCount) {
            RippleState(
                radius = Animatable(0f),
                alpha = Animatable(0.5f)  // Start with a higher alpha
            )
        }
    }

    val color = MaterialTheme.colorScheme.onBackground
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            ripples.forEachIndexed { index, ripple ->
                coroutineScope.launch {
                    delay(800L * index)
                    ripple.radius.snapTo(0f)
                    ripple.alpha.snapTo(0.8f)

                    launch {
                        ripple.radius.animateTo(
                            targetValue = 200f,
                            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                        )
                    }

                    launch {
                        ripple.alpha.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 2100, easing = LinearEasing)
                        )
                    }
                }
            }
            delay(2500)
        }
    }

    Box(modifier = Modifier.fillMaxSize(0.5f), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize(0.5f)) {
            rippleCenter = Offset(size.width / 2, size.height / 2)
            ripples.forEach { ripple ->
                drawCircle(
                    color = color.copy(alpha = ripple.alpha.value),
                    radius = ripple.radius.value,
                    center = rippleCenter
                )
            }
        }
    }
}

data class RippleState(
    val radius: Animatable<Float, AnimationVector1D>,
    val alpha: Animatable<Float, AnimationVector1D>
)