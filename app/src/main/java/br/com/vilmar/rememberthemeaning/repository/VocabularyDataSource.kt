package br.com.vilmar.rememberthemeaning.repository

import br.com.vilmar.rememberthemeaning.database.model.Vocabulary

interface VocabularyDataSource {

    fun getAll(): List<Vocabulary>

}