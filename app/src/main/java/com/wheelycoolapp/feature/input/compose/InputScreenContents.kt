package com.wheelycoolapp.feature.input.compose


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wheelycoolapp.R
import com.wheelycoolapp.core.ui.buttons.PrimaryButton

@Composable
fun InputScreenContents(
    modifier: Modifier = Modifier,
    onNewValue: (String) -> Unit,
    options: List<String>,
    onOpenWheelClicked: () -> Unit,
    isSelected: (Int) -> Boolean
) {
    Column(
        modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        OptionsInput(
            onNewValue = onNewValue
        )
        
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            options.forEachIndexed { index, item ->
                OptionItem(
                    modifier = Modifier.padding(vertical = 5.dp),
                    option = item,
                    isSelected = isSelected(index)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onOpenWheelClicked,
            text = stringResource(id = R.string.open_wheel)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputScreenContentsPreview() {
    val options = listOf("option 1", "option 2", "option 3")
    InputScreenContents(
        onNewValue = {},
        options = options,
        onOpenWheelClicked = {},
        isSelected = {
            false
        }
    )
}