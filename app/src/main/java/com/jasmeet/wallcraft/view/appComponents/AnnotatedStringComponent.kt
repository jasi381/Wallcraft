package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.pridi

@Composable
fun AnnotatedStringComponent(
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
    subTextFontFamily: FontFamily = pridi,
    onClick: () -> Unit = {},
    underlineSubText: Boolean = false
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
                textDecoration = if (underlineSubText) TextDecoration.Underline else TextDecoration.None
            )
        ) {
            append(subText)
            addStringAnnotation(
                tag = "Clickable",
                annotation = subText,
                start = length - subText.length,
                end = length
            )
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations("Clickable", offset, offset)
                .firstOrNull()?.let {
                    onClick()
                }
        },
        modifier = modifier
    )
}