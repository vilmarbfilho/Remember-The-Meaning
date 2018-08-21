package br.com.vilmar.rememberthemeaning.ui.main

import br.com.vilmar.rememberthemeaning.database.domain.Word
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.repository.VocabularyRepository

class VocabularyViewModel(private val vocabularyRepository: VocabularyRepository) {

    fun getVocabulary():  List<Word> {
        return transformVocabularyToWord(vocabularyRepository.getAll())
    }

    private fun transformVocabularyToWord(vocabulary: List<Vocabulary>) : List<Word> {
        val listTemp = mutableListOf<Word>()

        vocabulary.forEach {
            listTemp.add(Word(it.word, it.meaning, it.language))
        }

        return listTemp
    }

}