package com.wheelycoolapp.feature.input.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.wheelycoolapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsInput(
    onNewValue: (String) -> Unit
) {
    val value = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        singleLine = true,
        value = value.value,
        onValueChange = {
            value.value = it
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = {
                onNewValue(value.value)
                value.value = ""
                focusManager.clearFocus()
            }
        ),
        label = {
            Text(text = stringResource(id = R.string.add_new_option))
        },
        leadingIcon = {
            Icon(
                Icons.Outlined.List,
                contentDescription = null,
            )
        }
    )
}