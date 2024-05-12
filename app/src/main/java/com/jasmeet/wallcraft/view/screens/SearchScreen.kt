package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jasmeet.wallcraft.view.appComponents.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
//    searchViewModel: SearchViewModel = hiltViewModel()
) {

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

//    val searchResponse = searchViewModel.searchedResults.collectAsLazyPagingItems()
//    val error = searchViewModel.error.collectAsState()

//    if (error.value?.isNotEmpty() == true) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            NoInternetView()
//        }
//    } else {

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(Modifier.fillMaxWidth()) {
            SearchBar(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .statusBarsPadding()
            )
            Button(onClick = {
//                searchViewModel.loadPhotos(searchText)
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                Text(text = "Search")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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

    }


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


}