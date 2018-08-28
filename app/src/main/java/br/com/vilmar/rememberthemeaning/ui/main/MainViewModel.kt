package br.com.vilmar.rememberthemeaning.ui.main

import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val vocabularyRepository: VocabularyRepository): ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val uiEventLiveData = SingleLiveEvent<Int>()
    val vocabulary = SingleLiveEvent<List<Vocabulary>>()

    fun getVocabulary() {
        compositeDisposable.add(vocabularyRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {vocabulary.value = it})
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