package br.com.vilmar.rememberthemeaning.data.repository

import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary

class VocabularyRepository(val vocabularyDataSource: VocabularyDataSource) {

    fun getAll(): List<Vocabulary> {
        return vocabularyDataSource.getAll()
    }

}