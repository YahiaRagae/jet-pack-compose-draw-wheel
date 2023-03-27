package com.wheelycoolapp.feature.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wheelycoolapp.core.loading
import com.wheelycoolapp.core.mutableAsyncStateFlowOf
import com.wheelycoolapp.core.updateIntoSuccess
import com.wheelycoolapp.core.withSuccessfulState
import com.wheelycoolapp.domain.GetOptionsUseCase
import com.wheelycoolapp.domain.SetOptionsUseCase
import com.wheelycoolapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WheelViewModel @Inject constructor(
    private val getOptions: GetOptionsUseCase,
    private val setOptions: SetOptionsUseCase,
) : ViewModel() {

    val state = mutableAsyncStateFlowOf<WheelState>()
    val navEvent = MutableSharedFlow<Screen>()

    init {
        state.loading()
        loadOptions()
    }

    private fun loadOptions() {
        viewModelScope.launch {
            getOptions()
                .onSuccess { options ->
                    state.updateIntoSuccess {
                        (it ?: WheelState()).copy(
                            options = options
                        )
                    }
                }
                .onFailure {
                    state.updateIntoSuccess {
                        (it ?: WheelState()).copy(
                            options = emptyList()
                        )
                    }
                }
        }
    }

    private fun saveOptions() {
        state.withSuccessfulState {
            viewModelScope.launch {
                state.loading()
                setOptions(it.options).onSuccess {
                    state.updateIntoSuccess {
                        (it ?: WheelState()).copy()
                    }
                }
            }
        }
    }

    val onNewValue: (String) -> Unit = { newValue ->
        if (newValue.isNotEmpty()) {
            state.withSuccessfulState { successfulState ->
                val newList = successfulState.options.toMutableList().apply { add(newValue) }
                state.updateIntoSuccess {
                    successfulState.copy(
                        options = newList
                    )
                }
            }
        }
    }

    val onNewSelectedIndex: (Int) -> Unit = { newValue ->
        state.withSuccessfulState { successfulState ->
            state.updateIntoSuccess {
                val selectedItem = successfulState.options[newValue]
                successfulState.copy(
                    selectedItemIndex = newValue,
                    selectedItem = selectedItem,
                    isSpinning = false
                )
            }
        }
    }

    val onOpenWheelClicked: () -> Unit = {
        viewModelScope.launch {
            navEvent.emit(Screen.Wheel)
        }
    }

    fun onPause() {
        saveOptions()
    }

    fun onSpinButtonClicked() {
        state.withSuccessfulState { successfulState ->
            state.updateIntoSuccess {
                successfulState.copy(
                    isSpinning = true
                )
            }
        }
    }

    val isSelected: (Int) -> Boolean = {
        state.value.value?.selectedItemIndex == it
    }

    data class WheelState(
        val options: List<String> = emptyList(),
        val selectedItemIndex: Int = -1,
        val selectedItem: String = "",
        val isSpinning: Boolean = false
    )
}