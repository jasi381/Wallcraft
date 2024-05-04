package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jasmeet.wallcraft.viewModel.NetworkConnectivityViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, vm: NetworkConnectivityViewModel = viewModel()) {

    val isa = vm.isConnected.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            modifier = Modifier.statusBarsPadding(),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(text = "Home Screen", color = MaterialTheme.colorScheme.onBackground)
        Text(text = "Is Connected: ${isa.value}", color = MaterialTheme.colorScheme.onBackground)
    }
}

