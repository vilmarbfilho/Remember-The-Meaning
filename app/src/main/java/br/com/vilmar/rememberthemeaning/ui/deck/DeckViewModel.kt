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
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent
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

    private val _newWord = SingleLiveEvent<Unit>()
    private val _editWord = SingleLiveEvent<Vocabulary>()

    val newWord: LiveData<Unit> get() = _newWord
    val editWord: LiveData<Vocabulary> get() = _editWord

    val vocabularyListLiveData = MutableLiveData(emptyList<Vocabulary>())

    fun getVocabulary() {
        compositeDisposable.add(vocabularyRepository.getAll()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe {
                    vocabularyListLiveData.value = it
                })
    }

    fun search(word: String) {
        compositeDisposable.add(vocabularyRepository.search(word)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .debounce(TIME_DEBOUNCE_DEFAULT, TimeUnit.MILLISECONDS)
                .subscribe {
                    vocabularyListLiveData.value = it
                })
    }

    fun onItemClickVocabulary(position: Int) {
        _editWord.value = vocabularyListLiveData.value?.get(position).guard { return }
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
