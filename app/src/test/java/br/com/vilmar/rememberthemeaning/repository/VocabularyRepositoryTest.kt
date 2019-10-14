package br.com.vilmar.rememberthemeaning.repository

import br.com.vilmar.rememberthemeaning.common.BaseTest
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VocabularyRepositoryTest : BaseTest() {

    @Mock
    lateinit var vocabularyDataSource: VocabularyDataSource

    @InjectMocks
    lateinit var vocabularyRepository: VocabularyRepository

    @Test
    fun getAll_getVocabulary_showListSuccess() {
        val fakeValues = createVocabularyList()

        whenever(vocabularyDataSource.getAll()).thenReturn(fakeValues)

        val vocabularyList = vocabularyRepository.getAll().blockingGet()

        Truth
            .assertThat(vocabularyList)
            .containsExactlyElementsIn(fakeValues)
    }
}