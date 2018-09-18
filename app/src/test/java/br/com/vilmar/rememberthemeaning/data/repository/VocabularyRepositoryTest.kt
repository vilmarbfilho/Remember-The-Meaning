package br.com.vilmar.rememberthemeaning.data.repository

import br.com.vilmar.rememberthemeaning.data.database.model.Language
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class VocabularyRepositoryTest {

    @Mock
    lateinit var vocabularyDataSource: VocabularyDataSource

    @InjectMocks
    lateinit var vocabularyRepository: VocabularyRepository

    @Test
    fun getAll_success() {
        val fakeValues = createVocabularyList()

        whenever(vocabularyDataSource.getAll()).thenReturn(fakeValues)

        val testObserver = vocabularyRepository.getAll().test()

        testObserver.awaitTerminalEvent()

        testObserver
                .assertNoErrors()
                .assertValue(fakeValues)
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