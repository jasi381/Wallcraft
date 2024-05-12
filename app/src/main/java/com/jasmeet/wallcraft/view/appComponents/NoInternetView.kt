package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.R

@Composable
fun NoInternetView(error: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieComponent(
            rawRes = R.raw.lost_connection,
            modifier = Modifier
                .size(170.dp, 160.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        TextComponent(
            text = error,
            textSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textColor = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(5.dp))
//        TextComponent(
//            text = "Please check your internet connection",
//            textSize = 16.sp,
//            textColor = MaterialTheme.colorScheme.onBackground,
//        )

    }
}
