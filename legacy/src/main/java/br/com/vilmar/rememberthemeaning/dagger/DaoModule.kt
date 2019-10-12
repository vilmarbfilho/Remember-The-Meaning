package br.com.vilmar.rememberthemeaning.dagger

import android.content.Context
import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun provideVocabularyDao(context: Context): VocabularyDataSource = VocabularyDao(context)

}