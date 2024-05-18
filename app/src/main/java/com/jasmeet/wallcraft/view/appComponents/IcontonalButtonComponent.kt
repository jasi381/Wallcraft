package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun IconTonalButtonComponent(
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Int = 42
) {

    FilledTonalIconButton(
        onClick = {
            onClick.invoke()
        },

        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Color(
                Color(0xffF5F5F5).copy(alpha = 0.5f).toArgb()
            )
        ),
        modifier = modifier.size(size.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .size(45.dp),
            tint = Color.Black.copy(0.8f)
        )
    }

}

