package com.jasmeet.wallcraft.view.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.jasmeet.wallcraft.view.appComponents.LoaderView
import com.jasmeet.wallcraft.view.appComponents.PageNumberSelector
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
    onImageClicked: (Pair<String, String>) -> Unit,
) {
    BackHandler {
        onBackClick.invoke()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val response = categoryDetailsViewModel.details.collectAsState()
    val error = categoryDetailsViewModel.error.collectAsState()
    val isLoading = categoryDetailsViewModel.loading.collectAsState()

    var selectedPage by remember { mutableStateOf(1) }
    val gridState = rememberLazyStaggeredGridState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = selectedPage) {
        name?.let { categoryDetailsViewModel.getCategoryDetails(it, selectedPage) }
    }

    val shouldShowPagination by remember {
        derivedStateOf {
            val visibleItems = gridState.layoutInfo.visibleItemsInfo
            val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0
            val totalItems = response.value?.results?.size ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 1 // Show pagination when within 1 item from the end
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
                                val data = response.value?.results?.get(index)
                                val encodedUrl = URLEncoder.encode(data?.urls?.regular, "UTF-8")
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

                if (shouldShowPagination) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            PageNumberSelector(
                                selectedPage = selectedPage,
                                onPageSelected = { page ->
                                    selectedPage = page
                                    scope.launch {
                                        listState.scrollToItem(0)
                                        gridState.scrollToItem(0)
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            if (isLoading.value) {
                LoaderView()
            }
        }
    }
}
