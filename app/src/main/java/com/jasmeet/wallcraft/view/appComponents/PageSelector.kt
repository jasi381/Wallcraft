package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.poppins

@Composable
fun PageNumberSelector(
    selectedPage: Int,
    onPageSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (page in 1..5) {
            PageNumberButton(
                pageNumber = page,
                isSelected = page == selectedPage,
                onClick = { onPageSelected(page) }
            )
        }
    }
}

@Composable
fun PageNumberButton(
    pageNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor: Color =
        if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Transparent
    val textColor: Color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
    TextButton(
        onClick = onClick,
        modifier = Modifier.size(35.dp),
        colors = ButtonDefaults.textButtonColors(containerColor = containerColor)
    ) {
        Text(
            text = pageNumber.toString(),
            color = textColor,
            fontSize = 18.sp,
            fontFamily = poppins
        )
    }

}

