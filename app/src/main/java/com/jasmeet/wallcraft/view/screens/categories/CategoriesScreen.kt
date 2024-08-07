package com.jasmeet.wallcraft.view.screens.categories

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.CategoriesViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onImageClicked: (String) -> Unit
) {

    val categories = categoriesViewModel.categories.collectAsState()
    val isLoading = categoriesViewModel.isLoading.collectAsState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current


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
                scrollBehavior = scrollBehaviour
            )
        }
    ) { padding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(
                    bottom = paddingValues.calculateBottomPadding() + 10.dp,
                    top = padding.calculateTopPadding() + 10.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 12.dp,
        ) {


            items(
                categories.value.size,
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
                            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                        )
                    }
                    launch {
                        alpha.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                        )
                    }
                }


                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp * 2 / 6f)
                        .graphicsLayer {
                            this.scaleX = scale.value
                            this.scaleY = scale.value
                            this.alpha = alpha.value
                        }
                ) {
                    val data = categories.value[index]
                    val formattedText = Utils.getFirstWord(data?.title.toString())

                    data?.coverPhoto?.urls?.regular?.let { img ->
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(img)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.img_placeholder),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .clip(MaterialTheme.shapes.large)
                                .clickable {
                                    onImageClicked(formattedText)
                                }
                        )
                    }
                    TextComponent(
                        text = formattedText,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.BottomEnd)
                            .background(
                                Color.Black.copy(alpha = 0.5f),
                                MaterialTheme.shapes.small
                            )
                            .padding(4.dp),
                        textSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppins,
                        textColor = Color.White
                    )
                }


            }

        }

        if (isLoading.value) {
            Box(Modifier.fillMaxSize()) {
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
}