package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.domain.Word
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent

class MainViewModel(private val vocabularyRepository: VocabularyRepository): ViewModel(){

    val uiEventLiveData = SingleLiveEvent<Int>()
    val vocabulary = SingleLiveEvent<List<Word>>()

    fun getVocabulary() {
        vocabulary.value = transformVocabularyToWord(vocabularyRepository.getAll())
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