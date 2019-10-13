package br.com.vilmar.rememberthemeaning.ui.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.vilmar.rememberthemeaning.common.BaseTest
import br.com.vilmar.rememberthemeaning.common.testObserver
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : BaseTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun `on click settings open screen`() {
        val uiEventLiveData = viewModel.uiEventLiveData.testObserver()

        viewModel.openSettings()

        Truth.assert_()
            .that(uiEventLiveData.getValue())
            .isEqualTo(MainViewModel.OPEN_SETTINGS_SCREEN)
    }

    @Test
    fun `on click feedback open screen`() {
        val uiEventLiveData = viewModel.uiEventLiveData.testObserver()

        viewModel.openFeedback()

        Truth.assert_()
            .that(uiEventLiveData.getValue())
            .isEqualTo(MainViewModel.OPEN_FEEDBACK_SCREEN)
    }
}