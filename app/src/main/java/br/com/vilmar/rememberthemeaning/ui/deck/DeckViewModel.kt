package br.com.vilmar.rememberthemeaning.ui.deck

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.common.extensions.triggerEvent
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.executor.PostExecutionThread
import br.com.vilmar.rememberthemeaning.executor.ThreadExecutor
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeckViewModel @Inject constructor(
        private val vocabularyRepository: VocabularyRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread,
        private val compositeDisposable: CompositeDisposable): ViewModel() {


    private val _newWordScreen = SingleLiveEvent<Unit>()

    val newWordScreen: LiveData<Unit> get() = _newWordScreen

    val vocabularyListLiveData = SingleLiveEvent<List<Vocabulary>>()

    private val consumeInfo = { list : List<Vocabulary> ->
        vocabularyListLiveData.value = list
    }

    fun getVocabulary() {
        compositeDisposable.add(vocabularyRepository.getAll()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(consumeInfo))
    }

    fun search(word : String) {
        compositeDisposable.add(vocabularyRepository.search(word)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribe(consumeInfo))
    }

    fun openNewWordActivity() {
        _newWordScreen.triggerEvent()
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}