package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.pridi

@Composable
fun AnnotatedStringExample(
    modifier: Modifier,
    text: String,
    subText: String,
    textColor: Color = Color.White,
    subTextColor: Color = Color(0xffDF1F5A),
    textSize: TextUnit = 18.sp,
    subTextSize: TextUnit = 17.sp,
    textFontWeight: FontWeight = FontWeight.SemiBold,
    subTextFontWeight: FontWeight = FontWeight.Normal,
    textFontFamily: FontFamily = pridi,
    subTextFontFamily: FontFamily = pridi
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = textColor,
                fontWeight = textFontWeight,
                fontFamily = textFontFamily,
                fontSize = textSize
            )
        ) {
            append(text)
        }
        withStyle(
            style = SpanStyle(
                fontWeight = subTextFontWeight,
                color = subTextColor,
                fontFamily = subTextFontFamily,
                fontSize = subTextSize,

                )
        ) {
            append(subText)
        }
    }

    Text(text = annotatedString, modifier = modifier)
}