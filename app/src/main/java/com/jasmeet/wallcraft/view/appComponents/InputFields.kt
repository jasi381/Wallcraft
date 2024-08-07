package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.view.theme.poppins

@Composable
fun InputFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    readyOnly: Boolean = false,
    placeHolderColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
    fontFamily: FontFamily = poppins,
    fontSize: TextUnit = 17.sp,
    enabled: Boolean = true,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(0.5f),
            cursorColor = MaterialTheme.colorScheme.onBackground,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = Color.Blue.copy(0.5f)
            )
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        maxLines = 1,
        singleLine = true,
        readOnly = readyOnly,
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        enabled = enabled,
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                        color = placeHolderColor,
                    )
                )
            }
        },
        visualTransformation = VisualTransformation.None,
        interactionSource = remember { MutableInteractionSource() },
    )
}


@Composable
fun InputFieldComponent2(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    placeHolderColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
    fontFamily: FontFamily = poppins,
    fontSize: TextUnit = 17.sp,
    enabled: Boolean = true,
) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        modifier = modifier,
        textStyle = TextStyle(
            fontFamily = fontFamily,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        readOnly = readOnly,
        enabled = enabled,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                            0.2f
                        ),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = fontFamily,
                                fontSize = fontSize,
                                color = placeHolderColor,
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}
