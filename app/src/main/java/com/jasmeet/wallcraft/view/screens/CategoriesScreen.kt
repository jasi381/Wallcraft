package com.jasmeet.wallcraft.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.ScrollDirection
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.modifierExtensions.customClickable
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.CategoriesViewModel
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
    onScrollAction: (ScrollDirection) -> Unit,
) {

    val categories = categoriesViewModel.categories.collectAsState()

    val lazyListState = rememberLazyStaggeredGridState()
    var lastScrollPosition by remember { mutableIntStateOf(0) }
    var scrollDirection by remember { mutableStateOf(ScrollDirection.None) }
    val userInfo = loginSignUpViewModel.userInfo.collectAsState()

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        loginSignUpViewModel.getUserInfo()
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { currentScrollPosition ->
                scrollDirection = when {
                    currentScrollPosition > lastScrollPosition -> ScrollDirection.Down
                    currentScrollPosition < lastScrollPosition -> ScrollDirection.Up
                    else -> ScrollDirection.None
                }
                lastScrollPosition = currentScrollPosition

                onScrollAction(scrollDirection)
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

                    IconTonalButtonComponent(
                        icon = R.drawable.ic_sort,
                        onClick = {
                            categoriesViewModel.sortCategoriesAlphabetically()
                        },
                        size = 36
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
    ) {
        LazyVerticalStaggeredGrid(
            state = lazyListState,
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 12.dp,
        ) {
            items(
                count = categories.value.size,
                key = { keys ->
                    keys.toString()
                }) { index ->

                Column(
                    modifier = Modifier
                        .customClickable {
                            TODO()
                        }
                        .padding(10.dp)
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxWidth()
                ) {
                    categories.value[index]?.coverPhoto?.urls?.smallS3?.let { it1 ->
                        NetworkImage(
                            url = it1,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "data[index]?.altDescription",
                            modifier = Modifier
//                                .sharedElement(
//                                    state = rememberSharedContentState(
//                                        key = "image-${data[index]?.urls?.regular}"
//                                    ),
//                                    animatedVisibilityScope = animatedVisibilityScope,
//                                )
                                .height(LocalConfiguration.current.screenWidthDp.dp * 2 / 3f)
                                .clip(MaterialTheme.shapes.large)

                        )
                    }
                    categories.value[index]?.title?.let { it1 ->
                        TextComponent(
                            text = it1,
                            modifier = Modifier
                                .padding(10.dp),
                            textSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppins,
                        )
                    }
                }


            }

        }
    }


}