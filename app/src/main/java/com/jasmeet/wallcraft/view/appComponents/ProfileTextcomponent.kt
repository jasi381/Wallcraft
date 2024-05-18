package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileTextComponent(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = TextStyle(),
    value: String?,
    valueStyle: TextStyle = TextStyle(),
    shape: androidx.compose.ui.graphics.Shape = MaterialTheme.shapes.medium,
    color: Color = Color.LightGray,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = color),
        onClick = onClick
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                modifier = Modifier.padding(vertical = 14.dp),
                style = textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            value?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(vertical = 14.dp),
                    style = valueStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }


    }

}

@Preview
@Composable
private fun Prof() {
    ProfileTextComponent(
        title = "Followers",
        value = "1000"
    )


}