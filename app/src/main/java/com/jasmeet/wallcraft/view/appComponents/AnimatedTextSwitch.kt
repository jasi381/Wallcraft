package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.modifierExtensions.customClickable

@Composable
fun AnimatedTextSwitch(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enabledText: String,
    disabledText: String
) {
    AnimatedContent(
        targetState = isChecked,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        modifier = modifier.customClickable { onCheckedChange() }, label = ""
    ) { targetState ->
        TextComponent(
            text = if (targetState) enabledText else disabledText,
            textSize = 15.sp,
            textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f)
        )
    }
}