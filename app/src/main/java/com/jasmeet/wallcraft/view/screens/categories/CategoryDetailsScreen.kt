package com.jasmeet.wallcraft.view.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.CategoryDetailsViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.CategoryDetailsScreen(
    name: String?,
    onBackClick: () -> Boolean,
    animatedVisibilityScope: AnimatedContentScope,
    categoryDetailsViewModel: CategoryDetailsViewModel = hiltViewModel()
) {
    BackHandler {
        onBackClick.invoke()
    }

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val response = categoryDetailsViewModel.details.collectAsState()
    val error = categoryDetailsViewModel.error.collectAsState()
    val isLoading = categoryDetailsViewModel.loading.collectAsState()

    LaunchedEffect(key1 = Unit) {
        name?.let { categoryDetailsViewModel.getCategoryDetails(it) }
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
                            modifier = Modifier.sharedBounds(
                                rememberSharedContentState(
                                    key = "text-$name"
                                ),
                                animatedVisibilityScope,
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
                scrollBehavior = scrollBehaviour
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
            }
        }
    }

}