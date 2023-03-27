package com.wheelycoolapp

import com.wheelycoolapp.core.updateIntoSuccess
import com.wheelycoolapp.domain.GetOptionsUseCase
import com.wheelycoolapp.domain.SetOptionsUseCase
import com.wheelycoolapp.feature.input.WheelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WheelViewModelTest {
    private lateinit var viewModel: WheelViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val getOptionsUseCase = mock<GetOptionsUseCase> {
            onBlocking { invoke() } doReturn Result.success(
                listOf(
                    "Option 1",
                    "Option 2",
                    "Option 3"
                )
            )
        }
        val setOptionsUseCase = mock<SetOptionsUseCase> {}

        viewModel = WheelViewModel(getOptionsUseCase, setOptionsUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onNewValue should add new value to options list`() {
        val newValue = "Option 4"
        val state = WheelViewModel.WheelState(options = listOf("Option 1", "Option 2", "Option 3"))


        viewModel.state.updateIntoSuccess { state }
        viewModel.onNewValue(newValue)

        assert(
            viewModel.state.value.value?.options == listOf(
                "Option 1",
                "Option 2",
                "Option 3",
                "Option 4"
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadOptions should update state with options on success`() = runTest {
        val options: List<String> = listOf("Option 1")

        val getOptionsUseCase = mock<GetOptionsUseCase> {
            onBlocking { invoke() } doReturn Result.success(
                options
            )
        }
        val setOptionsUseCase = mock<SetOptionsUseCase> {}
        viewModel = WheelViewModel(getOptionsUseCase, setOptionsUseCase)

        assert(viewModel.state.value.value?.options == options)
    }

    @Test
    fun `onNewSelectedIndex should update selected item and isSpinning`() {
        val state = WheelViewModel.WheelState(options = listOf("Option 1", "Option 2", "Option 3"))

        viewModel.state.updateIntoSuccess { state }
        viewModel.onNewSelectedIndex(1)


        assert(viewModel.state.value.value?.selectedItemIndex == 1)
        assert(viewModel.state.value.value?.selectedItem == "Option 2")
        assert(viewModel.state.value.value?.isSpinning == false)
    }


    @Test
    fun `isSelected should return true for selected item index`() {
        val state = WheelViewModel.WheelState(
            options = listOf("Option 1", "Option 2", "Option 3"),
            selectedItemIndex = 1
        )

        viewModel.state.updateIntoSuccess { state }
        val isSelected = viewModel.isSelected(1)

        assert(isSelected)
    }
}
