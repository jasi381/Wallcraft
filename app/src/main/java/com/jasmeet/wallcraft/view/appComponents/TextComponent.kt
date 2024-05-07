package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.pridi

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textSize: TextUnit = 18.sp,
    fontFamily: FontFamily = pridi,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    enableShadow: Boolean = false,
    maxLines: Int = 2,
    lineHeight: TextUnit = TextUnit.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            lineHeight = lineHeight,
            fontSize = textSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            color = textColor,
            textAlign = textAlign,
            shadow = if (enableShadow) Shadow(
                Color.Red,
                offset = Offset(3.0f, 4.0f),
                blurRadius = 3f
            ) else null
        ),
        maxLines = maxLines,
        softWrap = true,
        overflow = TextOverflow.Ellipsis
    )

}