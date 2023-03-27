package com.wheelycoolapp.feature.input.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    option: String,
    isSelected: Boolean
) {
    val backgroundColor =
        if (isSelected) Color.Yellow else MaterialTheme.colorScheme.primaryContainer

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Outlined.KeyboardArrowRight,
            contentDescription = null
        )
        Text(
            text = option,
            style = MaterialTheme.typography.titleMedium
        )
    }

}