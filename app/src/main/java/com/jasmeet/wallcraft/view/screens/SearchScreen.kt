package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.SearchBar
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
//    searchViewModel: SearchViewModel = hiltViewModel()
) {

    var searchText by rememberSaveable {
        mutableStateOf("")
    }
    val userInfo = loginSignUpViewModel.userInfo.collectAsState()
    LaunchedEffect(Unit) {
        loginSignUpViewModel.getUserInfo()
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

    Scaffold(
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

            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            Row(Modifier.fillMaxWidth()) {
                SearchBar(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier,
                    onClear = {
                        searchText = ""
                    },
                    labelValue = "Enter your query..."

                )
                TextButton(onClick = {
                    //                searchViewModel.loadPhotos(searchText)
                }) {
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