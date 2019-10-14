package br.com.vilmar.rememberthemeaning.data.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

class VocabularyRepository @Inject constructor(private val vocabularyDataSource: VocabularyDataSource) {

    fun getAll() = Maybe.just(vocabularyDataSource.getAll())

    fun search(word : String) = Flowable.just(vocabularyDataSource.search(word))

}