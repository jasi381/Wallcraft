package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.theme.poppins

@Composable
fun PassWordTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Password,
    readyOnly: Boolean = false,
    placeHolderColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
    fontFamily: FontFamily = poppins,
    fontSize: TextUnit = 17.sp,
    enabled: Boolean = true,
    onDone: () -> Unit = {},
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
            ),
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone.invoke()
            }
        ),
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
        trailingIcon = {

            val iconImage =
                if (passwordVisible)
                    R.drawable.ic_show
                else
                    R.drawable.ic_hide

            val description = if (passwordVisible)
                "Hide Password"
            else
                "Show Password"

            IconButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = iconImage),
                    contentDescription = description,
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.85f),
                )
            }
        },
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation('●'),

        )


}