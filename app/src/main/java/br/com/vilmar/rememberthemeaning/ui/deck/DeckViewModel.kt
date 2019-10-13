package br.com.vilmar.rememberthemeaning.ui.deck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.common.extensions.guard
import br.com.vilmar.rememberthemeaning.common.extensions.triggerEvent
import br.com.vilmar.rememberthemeaning.data.database.model.Vocabulary
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.executor.PostExecutionThread
import br.com.vilmar.rememberthemeaning.executor.ThreadExecutor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeckViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _newWord = MutableLiveData<Unit>()
    private val _editWord = MutableLiveData<Vocabulary>()
    private val _vocabularyItems = MutableLiveData(emptyList<Vocabulary>())

    val newWord: LiveData<Unit> get() = _newWord
    val editWord: LiveData<Vocabulary> get() = _editWord
    val vocabularyItems: LiveData<List<Vocabulary>> get() = _vocabularyItems


    fun getVocabulary() {
        compositeDisposable.add(vocabularyRepository.getAll()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe {
                    _vocabularyItems.value = it
                })
    }

    fun search(word: String) {
        compositeDisposable.add(vocabularyRepository.search(word)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .debounce(TIME_DEBOUNCE_DEFAULT, TimeUnit.MILLISECONDS)
                .subscribe {
                    _vocabularyItems.value = it
                })
    }

    fun onItemClickVocabulary(position: Int) {
        _editWord.value = _vocabularyItems.value?.get(position).guard { return }
    }

    fun openNewWordActivity() {
        _newWord.triggerEvent()
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    companion object {
        private const val TIME_DEBOUNCE_DEFAULT = 300L
    }
}
