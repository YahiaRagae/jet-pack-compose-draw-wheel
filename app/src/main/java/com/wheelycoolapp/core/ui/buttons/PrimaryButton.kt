package com.wheelycoolapp.core.ui.buttons

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) = Button(
    modifier = modifier.widthIn(min = 150.dp),
    onClick = { onClick() },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary
    ),
    shape = RoundedCornerShape(30.dp)
) {
    Text(
        modifier = Modifier.align(Alignment.CenterVertically),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Center
    )
}