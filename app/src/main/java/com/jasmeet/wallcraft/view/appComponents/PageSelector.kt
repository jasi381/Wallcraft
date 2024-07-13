package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent
    val textColor =
        if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
            alpha = 0.7f
        )

    Button(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = pageNumber.toString(),
            color = textColor,
            fontSize = 18.sp,
            fontFamily = poppins
        )
    }
}