package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.domain.Word
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val vocabularyRepository: VocabularyRepository): ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val uiEventLiveData = SingleLiveEvent<Int>()
    val vocabulary = SingleLiveEvent<List<Word>>()

    fun getVocabulary() {
        compositeDisposable.add(vocabularyRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {vocabulary.value = transformVocabularyToWord(it)})

        //vocabulary.value = transformVocabularyToWord(vocabularyRepository.getAll())
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

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}