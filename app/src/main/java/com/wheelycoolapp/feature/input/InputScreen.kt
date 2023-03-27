package com.wheelycoolapp.feature.input

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.wheelycoolapp.core.onLoading
import com.wheelycoolapp.core.onSuccess
import com.wheelycoolapp.core.ui.Loading
import com.wheelycoolapp.core.ui.OnLifecycleEvent
import com.wheelycoolapp.feature.input.compose.InputScreenContents
import com.wheelycoolapp.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    modifier: Modifier = Modifier,
    viewModel: WheelViewModel = hiltViewModel(),
    navigateTo: (Screen) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = context) {
        launch {
            viewModel.navEvent.collect {
                navigateTo(it)
            }
        }
    }

    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_PAUSE) {
            viewModel.onPause()
        }
    }

    Scaffold { padding ->
        state.value
            .onLoading {
                Loading()
            }
            .onSuccess {
                InputScreenContents(
                    modifier = modifier.padding(padding),
                    onNewValue = viewModel.onNewValue,
                    options = it.options,
                    onOpenWheelClicked = viewModel.onOpenWheelClicked,
                    isSelected = viewModel.isSelected
                )
            }
    }
}