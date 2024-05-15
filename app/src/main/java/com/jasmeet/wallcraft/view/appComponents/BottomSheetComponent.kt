package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    content: @Composable () -> Unit,

    ) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        shape = shape
    ) {
        content()

    }

}