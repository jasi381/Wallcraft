package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ThreeDBlinkingBorderImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedFactor by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val color = MaterialTheme.colorScheme.onBackground

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = (size.minDimension - borderWidth.toPx() * 2) / 2

            // Outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color,
                        color.copy(alpha = 0.5f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius + borderWidth.toPx() * 2
                ),
                radius = radius + borderWidth.toPx() * 2,
                center = center
            )

            // Main border circles
            for (i in 0..2) {
                drawCircle(
                    color = lerp(primaryColor, secondaryColor, (animatedFactor + i / 3f) % 1f),
                    style = Stroke(width = borderWidth.toPx() / 3),
                    radius = radius + i * borderWidth.toPx() / 3,
                    center = center
                )
            }

            // Inner highlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x60FFFFFF),
                        Color.Transparent
                    ),
                    center = Offset(center.x * 0.8f, center.y * 0.8f),
                    radius = radius * 0.5f
                ),
                radius = radius,
                center = center
            )
        }

        // Main image
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .layout { measurable, constraints ->
                    val borderWidthPx = borderWidth.roundToPx()
                    val imageSize = constraints.maxWidth - 2 * borderWidthPx
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = imageSize,
                            maxWidth = imageSize,
                            minHeight = imageSize,
                            maxHeight = imageSize
                        )
                    )
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        placeable.place(borderWidthPx, borderWidthPx)
                    }
                }
                .clip(CircleShape)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}

fun lerp(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = lerp(start.red, end.red, fraction),
        green = lerp(start.green, end.green, fraction),
        blue = lerp(start.blue, end.blue, fraction),
        alpha = lerp(start.alpha, end.alpha, fraction)
    )
}

fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}
