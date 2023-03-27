package com.wheelycoolapp.feature.wheel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wheelycoolapp.R
import com.wheelycoolapp.core.onLoading
import com.wheelycoolapp.core.onSuccess
import com.wheelycoolapp.core.ui.Loading
import com.wheelycoolapp.core.ui.buttons.PrimaryButton
import com.wheelycoolapp.feature.input.WheelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheelScreen(
    modifier: Modifier = Modifier,
    viewModel: WheelViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Scaffold { padding ->
        state.value
            .onLoading {
                Loading()
            }
            .onSuccess {
                Column(modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
                    PokerWheel(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Yellow),
                        items = it.options,
                        isSpinning = it.isSpinning,
                        onSpinningDone = viewModel.onNewSelectedIndex
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Selected item: ${it.selectedItem}", fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    val title = when (it.isSpinning) {
                        true -> stringResource(id = R.string.spining)
                        false -> stringResource(id = R.string.spin_the_wheel)
                    }

                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onSpinButtonClicked() },
                        text = title
                    )

                }
            }
    }
}