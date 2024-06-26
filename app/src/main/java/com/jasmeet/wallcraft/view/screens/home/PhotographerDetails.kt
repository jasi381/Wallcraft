package com.jasmeet.wallcraft.view.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.PhotographerDetailsViewModel
import java.net.URLEncoder

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PhotographerDetailsScreen(
    name: String?,
    url: String?,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    photographerDetailsViewModel: PhotographerDetailsViewModel = hiltViewModel(),
    userName: String?,
    onImageClicked: (Pair<String, String>) -> Unit,
) {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val details = photographerDetailsViewModel.details.collectAsState()
    val photos = photographerDetailsViewModel.photos.collectAsState()

    BackHandler {
        onBackClick.invoke()
    }

    LaunchedEffect(Unit) {
        name?.let { photographerDetailsViewModel.getDetails(it) }
        userName?.let { photographerDetailsViewModel.getPhotos(it) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                title = {
                    name?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconTonalButtonComponent(
                        icon = R.drawable.ic_back,
                        onClick = { onBackClick.invoke() }
                    )
                },
                actions = {
                    val instagramUsername = details.value?.results?.first()?.social?.instagramUsername
                    val twitterUsername = details.value?.results?.first()?.social?.twitterUsername

                    if (instagramUsername != null && twitterUsername != null) {
                        // Display Twitter icon only
                        IconButton(
                            onClick =  { Utils.openUrlInBrowser(context, "https://www.twitter.com/$twitterUsername") }
                        ) {
                            Image(painter = painterResource(R.drawable.img_twitter), "")
                        }
                    } else if (instagramUsername != null) {
                        // Display Instagram icon only
                        IconButton(
                            onClick =  { Utils.openUrlInBrowser(context, "https://www.instagram.com/$instagramUsername") }
                        ) {
                            Image(painter = painterResource(R.drawable.img_instagram), "")
                        }
                    }


                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .nestedScroll(scrollBehaviour.nestedScrollConnection)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {

            url?.let {
                NetworkImage(
                    url = it,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image-$url"),
                            animatedVisibilityScope,
                        )
                        .padding(top = 10.dp)
                        .size(140.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .border(2.dp, MaterialTheme.colorScheme.onBackground, CircleShape)

                )

            }


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = "Photos",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 15.dp),
                    textSize = 20.sp
                )
                Box(contentAlignment = Alignment.BottomEnd) {

                    var expanded by remember { mutableStateOf(false) }


                    IconTonalButtonComponent(
                        icon = R.drawable.ic_sort,
                        onClick = {
                            expanded = true
                        },
                        size = 36
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Latest") },
                            onClick = {
                                userName?.let {
                                    photographerDetailsViewModel.getPhotos(
                                        it,
                                        OrderBy.LATEST.displayName
                                    )
                                }
                                expanded = false
                            }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Popular") },
                            onClick = {
                                userName?.let {
                                    photographerDetailsViewModel.getPhotos(
                                        it,
                                        OrderBy.POPULAR.displayName
                                    )
                                }
                                expanded = false
                            }
                        )

                    }

                }
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp,
            ) {
                items(photos.value) { response ->

                    val image = URLEncoder.encode(response?.urls?.regular, "UTF-8")

                    NetworkImage(
                        url = response?.urls?.regular.toString(),
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(
                                    key = "image-${response?.urls?.regular}"
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                            .clip(MaterialTheme.shapes.large)
                            .height(LocalConfiguration.current.screenHeightDp.dp * 0.25f)
                            .width(LocalConfiguration.current.screenWidthDp.dp * 0.26f)
                            .clickable {
                                onImageClicked(Pair(image, response?.id.toString()))
                            },
                        contentScale = ContentScale.FillBounds
                    )
                }


            }


        }
    }
}