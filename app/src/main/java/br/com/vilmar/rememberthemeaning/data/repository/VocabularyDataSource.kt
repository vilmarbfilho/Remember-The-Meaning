package br.com.vilmar.rememberthemeaning.data.repository

import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary

interface VocabularyDataSource {

    fun getAll(): List<Vocabulary>

}