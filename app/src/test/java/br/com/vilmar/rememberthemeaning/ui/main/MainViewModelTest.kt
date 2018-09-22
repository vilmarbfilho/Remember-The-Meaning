package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.vilmar.rememberthemeaning.common.BaseTest
import br.com.vilmar.rememberthemeaning.common.RxSchedulerRule
import br.com.vilmar.rememberthemeaning.common.testObserver
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: BaseTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var vocabularyDataSource: VocabularyDataSource

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(VocabularyRepository(vocabularyDataSource))
    }

    @Test
    fun `on start set vocabulary list`() {

        val fakeValues = createVocabularyList()

        whenever(vocabularyDataSource.getAll()).thenReturn(fakeValues)

        val vocabularyLiveData = viewModel.vocabulary.testObserver()

        viewModel.getVocabulary()

        Truth.assert_()
                .that(vocabularyLiveData.getValue())
                .isEqualTo(fakeValues)
    }

}