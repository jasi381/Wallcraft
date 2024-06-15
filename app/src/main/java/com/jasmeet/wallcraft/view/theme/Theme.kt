package com.jasmeet.wallcraft.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = Color(0xff131521),
    onBackground = Color(0xffffffff),
    primaryContainer = Color(0xff041e49),
    onPrimaryContainer = Color(0xffd3e3fd),
    primary = Color.Black,
    surface = Color(0xff424242),
    tertiaryContainer = Color(0xff0C0404),
    onSurface = Color(0xff262A34)
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xffF5F5F5),
    onBackground = Color(0xff000000),
    primaryContainer = Color(0xffffffff),
    onPrimaryContainer = Color(0xff1b6ef3),
    primary = Color.White,
    surface = Color(0xffffffff),
    tertiaryContainer = Color(0xffF8F8FF),
    onSurface = Color(0x99757575)
)

@Composable
fun WallcraftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}