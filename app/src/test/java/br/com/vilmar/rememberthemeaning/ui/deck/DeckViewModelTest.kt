package br.com.vilmar.rememberthemeaning.ui.deck

import android.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.vilmar.rememberthemeaning.common.BaseTest
import br.com.vilmar.rememberthemeaning.common.RxSchedulerRule
import br.com.vilmar.rememberthemeaning.common.testObserver
import br.com.vilmar.rememberthemeaning.common.verify
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.executor.JobExecutor
import br.com.vilmar.rememberthemeaning.executor.UIThread
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeckViewModelTest: BaseTest() {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var vocabularyDataSource: VocabularyDataSource

    lateinit var viewModel: DeckViewModel

    @Before
    fun setUp() {
        viewModel = DeckViewModel(
                VocabularyRepository(vocabularyDataSource),
                JobExecutor(),
                UIThread(),
                CompositeDisposable())
    }

    @Test
    fun `on start set vocabulary list`() {

        val fakeValues = createVocabularyList()

        whenever(vocabularyDataSource.getAll()).thenReturn(fakeValues)

        val vocabularyListLiveData = viewModel.vocabularyListLiveData.testObserver()

        viewModel.getVocabulary()

        Truth.assert_()
                .that(vocabularyListLiveData.getValue())
                .isEqualTo(fakeValues)
    }

    @Test
    fun `on click plus button open new word activity`() {
        viewModel.openNewWordActivity()

        viewModel.newWordScreen.verify()
    }

    /* At the moment is not possible make this test
    @Test
    fun `on click in card open word activity`() {
        val position = 1
        val fakeValues = createVocabularyList()
        val wordEventLiveData = viewModel.wordEventLiveData.testObserver()

        whenever(vocabularyDataSource.getAll()).thenReturn(fakeValues)

        viewModel.getVocabulary()
        viewModel.openWordActivity(position)

        Truth.assert_()
                .that(wordEventLiveData.getValue())
                .isEqualTo(fakeValues[position])
    }*/


}