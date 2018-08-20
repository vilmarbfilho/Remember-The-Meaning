package br.com.vilmar.rememberthemeaning.ui.main

import br.com.vilmar.rememberthemeaning.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.repository.VocabularyRepository

class VocabularyViewModel(private val vocabularyRepository: VocabularyRepository) {

    fun getVocabulary():  List<Vocabulary> {
        return vocabularyRepository.getAll()
    }

}