package com.jasmeet.wallcraft.view.screens.home

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.OrderBy
import com.jasmeet.wallcraft.view.appComponents.IconTonalButtonComponent
import com.jasmeet.wallcraft.view.appComponents.NetworkImage
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.appComponents.ThreeDBlinkingBorderImage
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
    onImageClicked: (Triple<String, String, String>) -> Unit,
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
                        IconButton(onClick = {
                            openTab(
                                context,
                                "https://www.twitter.com/$twitterUsername"
                            )
                        }) {
                            Image(painter = painterResource(R.drawable.img_twitter), "")
                        }
                    } else if (instagramUsername != null) {
                        IconButton(onClick = {
                            openTab(
                                context,
                                "https://www.instagram.com/$instagramUsername"
                            )
                        }) {
                            Image(painter = painterResource(R.drawable.img_instagram), "")
                        }
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .nestedScroll(scrollBehaviour.nestedScrollConnection)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = url)
                        .build()
                )
                ThreeDBlinkingBorderImage(
                    painter = painter,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image-$url"),
                            animatedVisibilityScope,
                            boundsTransform = { initialRect, targetRect ->
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
                        .size(110.dp),
                    borderWidth = 5.dp
                )
            }

            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        text = "Photos",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 15.dp, horizontal = 15.dp),
                        textSize = 20.sp
                    )
                    Box(contentAlignment = Alignment.BottomEnd) {
                        var expanded by remember { mutableStateOf(false) }
                        IconTonalButtonComponent(
                            icon = R.drawable.ic_sort,
                            onClick = { expanded = true },
                            size = 36
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Latest",
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                },
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
                                text = {
                                    Text(
                                        "Popular",
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                },
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
            }

            itemsIndexed(photos.value.chunked(3)) { _, rowPhotos ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowPhotos.forEach { response ->
                        val image = URLEncoder.encode(response?.urls?.regular, "UTF-8")
                        val lowImage = URLEncoder.encode(response?.urls?.small, "UTF-8")

                        NetworkImage(
                            url = response?.urls?.regular.toString(),
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image-${response?.urls?.regular}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { initialRect, targetRect ->
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
                                .clip(MaterialTheme.shapes.large)
                                .height(LocalConfiguration.current.screenHeightDp.dp * 0.25f)
                                .weight(1f)
                                .clickable {
                                    onImageClicked(Triple(image, response?.id.toString(), lowImage))
                                },
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    // Fill empty spaces in the last row
                    repeat(3 - rowPhotos.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

    }
}

fun openTab(context: Context, url: String) {

    val packageName = "com.android.chrome"
    val builder = CustomTabsIntent.Builder()
    builder.setInstantAppsEnabled(true)
    val customBuilder = builder.build()
    customBuilder.intent.setPackage(packageName)
    customBuilder.launchUrl(context, Uri.parse(url))
}