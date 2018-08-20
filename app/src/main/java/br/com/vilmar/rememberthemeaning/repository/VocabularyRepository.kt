package br.com.vilmar.rememberthemeaning.repository

import br.com.vilmar.rememberthemeaning.database.model.Vocabulary

class VocabularyRepository(val vocabularyDataSource: VocabularyDataSource) {

    fun getAll(): List<Vocabulary> {
        return vocabularyDataSource.getAll()
    }

}