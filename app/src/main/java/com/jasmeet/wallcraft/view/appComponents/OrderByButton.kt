package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.poppins

@Composable
fun OrderByButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = Color.Transparent
    ) {

        TextComponent(
            text = text,
            textSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            textColor = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.6f
            ),
            modifier = Modifier
                .background(
                    if (isSelected) Color(0xff725ffe) else Color.Transparent,
                    MaterialTheme.shapes.large
                )
                .padding(vertical = 2.dp, horizontal = 8.dp)
        )
    }
}
