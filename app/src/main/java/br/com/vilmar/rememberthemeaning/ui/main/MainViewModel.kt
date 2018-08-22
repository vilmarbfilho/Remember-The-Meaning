package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.database.domain.Word
import br.com.vilmar.rememberthemeaning.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent

class MainViewModel(private val vocabularyRepository: VocabularyRepository): ViewModel(){

    val uiEventLiveData = SingleLiveEvent<Int>()

    fun getVocabulary():  List<Word> {
        return transformVocabularyToWord(vocabularyRepository.getAll())
    }

    private fun transformVocabularyToWord(vocabulary: List<Vocabulary>): List<Word> {
        val listTemp = mutableListOf<Word>()

        vocabulary.forEach {
            listTemp.add(Word(it.word, it.meaning, it.language))
        }

        return listTemp
    }

    fun startNewWordActivity() {
        uiEventLiveData.value = 1
    }

}