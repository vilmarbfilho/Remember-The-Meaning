package br.com.vilmar.rememberthemeaning.ui.main

import br.com.vilmar.rememberthemeaning.common.testObserver
import br.com.vilmar.rememberthemeaning.data.database.model.Language
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    //@get:Rule
    //val mockitoRule = MockitoJUnit.rule()

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
                .that(vocabularyLiveData.observedValues)
                .isEqualTo(fakeValues)
    }

    private fun createVocabularyList(): List<Vocabulary> {
        val list = mutableListOf<Vocabulary>()

        val language = Language("EN")

        list.add(Vocabulary(true, "Word", "Meaning", 1000L, language))
        list.add(Vocabulary(true, "Girl", "Menina", 1000L, language))
        list.add(Vocabulary(true, "Boy", "Menino", 1000L, language))
        list.add(Vocabulary(true, "Boat", "Barco", 1000L, language))
        list.add(Vocabulary(true, "House", "Casa", 1000L, language))

        return list
    }
}