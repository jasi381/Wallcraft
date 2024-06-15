package com.jasmeet.wallcraft.view.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
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
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    onImageClicked: (Pair<String, String>) -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
) {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val activity = (LocalContext.current as? Activity)
    val data = homeViewModel.homeData.collectAsLazyPagingItems()
    val error = homeViewModel.error.collectAsState()
    val context = LocalContext.current
    val userInfo = loginSignUpViewModel.userInfo.collectAsState()

    val lazyListState = rememberLazyStaggeredGridState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        loginSignUpViewModel.getUserInfo()
    }

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
                navigationIcon = {
                    AsyncImage(
                        model = userInfo.value?.imgUrl,
                        contentDescription = "userInfo",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .size(38.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.img_placeholder)

                    )
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
                NoInternetView(error = error.value!!)
            }
        } else {
            Column(
                Modifier
                    .offset(
                        y = (-12).dp
                    )
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {

                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    visible = (lazyListState.firstVisibleItemIndex <= 0),
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(LocalConfiguration.current.screenHeightDp.dp)
                ) {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(150.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalItemSpacing = 12.dp,
                    ) {

                        items(
                            count = data.itemCount,
                            key = {
                                it.toString()
                            }
                        ) { index ->

                            val encodedUrl =
                                URLEncoder.encode(data[index]?.urls?.regular, "UTF-8")

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
                                    .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
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
    }
}







