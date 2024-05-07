package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun IconTonalButtonComponent(icon: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {

    FilledTonalIconButton(
        onClick = {
            onClick.invoke()
        },

        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Color(
                MaterialTheme.colorScheme.background.copy(alpha = 0.4f).toArgb()
            )
        ),
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .size(45.dp)
        )
    }

}

