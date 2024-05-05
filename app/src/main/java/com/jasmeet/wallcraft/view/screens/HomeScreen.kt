package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.viewModel.HomeViewModel
import com.jasmeet.wallcraft.viewModel.NetworkConnectivityViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: NetworkConnectivityViewModel = viewModel(),
    hv: HomeViewModel = hiltViewModel()
) {

    val isa = vm.isConnected.collectAsState()
    val context = LocalContext.current

    val dta = hv.homeData.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        hv.loadData()

    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Home Screen",
//            modifier = Modifier.statusBarsPadding(),
//            color = MaterialTheme.colorScheme.onBackground
//        )
//        Text(text = "Home Screen", color = MaterialTheme.colorScheme.onBackground)
//        Text(text = "Is Connected: ${isa.value}", color = MaterialTheme.colorScheme.onBackground)
//
//    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(dta.itemCount) { index ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dta[index]?.src?.portrait)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(MaterialTheme.shapes.large)
            )
        }

    }
}

