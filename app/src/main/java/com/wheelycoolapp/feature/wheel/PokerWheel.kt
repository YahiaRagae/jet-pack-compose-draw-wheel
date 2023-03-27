package com.wheelycoolapp.feature.wheel


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@OptIn(ExperimentalTextApi::class)
@Composable
fun PokerWheel(
    modifier: Modifier = Modifier,
    items: List<String>,
    isSpinning: Boolean,
    onSpinningDone: (Int) -> Unit
) {
    var startAngle by remember { mutableStateOf(0f) }
    var selectedItemIndex by remember { mutableStateOf(-1) }

    val textMeasurer = rememberTextMeasurer()
    val arcSize = remember { mutableStateOf(0f) }
    val wheelRadius = remember { mutableStateOf(0f) }
    val centerX = remember { mutableStateOf(0f) }
    val centerY = remember { mutableStateOf(0f) }
    val itemColors = remember {
        items.map {
            Color(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
        }
    }

    var rotation by remember { mutableStateOf(0f) }
    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            var elapsedTime = 0L
            val duration = Random.nextLong(1500, 3000)
            while (elapsedTime < duration) {
                delay(16)
                elapsedTime += 16
                rotation += 2f
                rotation %= 360
            }

            onSpinningDone(selectedItemIndex)
        }
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(30.dp)
                .aspectRatio(1f)
                .background(Color.Gray, CircleShape)
                .onSizeChanged { size ->
                    val diameter = size.width.coerceAtMost(size.height)
                    wheelRadius.value = diameter / 2f
                    centerX.value = size.width / 2f
                    centerY.value = size.height / 2f
                    arcSize.value = 360f / items.size.toFloat()
                }
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                for (i in items.indices) {
                    val angle = (arcSize.value * i + rotation - arcSize.value * 2) % 360
                    val radian = Math.toRadians(angle.toDouble())
                    val x = centerX.value + wheelRadius.value * cos(radian)
                    val y = centerY.value + wheelRadius.value * sin(radian)

                    drawLine(
                        color = Color.Black,
                        start = Offset(x.toFloat(), y.toFloat()),
                        end = Offset(centerX.value, centerY.value),
                        strokeWidth = 10f
                    )

                    drawArc(
                        color = itemColors[i],
                        startAngle = angle,
                        sweepAngle = arcSize.value,
                        useCenter = true,
                        size = Size(
                            width = wheelRadius.value * 2f,
                            height = wheelRadius.value * 2f,
                        ),
                        topLeft = Offset(
                            x = centerX.value - wheelRadius.value,
                            y = centerY.value - wheelRadius.value
                        )
                    )
                }

                for (i in items.indices) {
                    val angle = (arcSize.value * i + rotation + arcSize.value / 2) % 360
                    val radian = Math.toRadians(angle.toDouble())
                    val x = centerX.value + wheelRadius.value / 2 * cos(radian)
                    val y = centerY.value + wheelRadius.value / 2 * sin(radian)

                    drawText(
                        textMeasurer = textMeasurer,
                        text = items[i],
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        ),
                        topLeft = Offset(x.toFloat(), y.toFloat())
                    )
                }

                drawLine(
                    color = Color.Red,
                    start = Offset(size.width, centerY.value),
                    end = Offset(size.width - 130, centerY.value),
                    strokeWidth = 4f
                )

            }
        }
        selectedItemIndex =
            (((-rotation + 360) % 360 + 360) % 360 / (360 / items.size)).toInt()

    }
}