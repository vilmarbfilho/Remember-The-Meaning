package br.com.vilmar.rememberthemeaning.data.repository

import io.reactivex.Maybe

class VocabularyRepository(val vocabularyDataSource: VocabularyDataSource) {

    fun getAll() = Maybe.just(vocabularyDataSource.getAll())

}