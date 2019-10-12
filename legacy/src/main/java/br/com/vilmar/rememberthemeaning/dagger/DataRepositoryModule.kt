package br.com.vilmar.rememberthemeaning.dagger

import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import dagger.Module
import dagger.Provides

@Module
class DataRepositoryModule {

    @Provides
    fun provideVocabularyRepository(vocabularyDao: VocabularyDao) = VocabularyRepository(vocabularyDao)

}