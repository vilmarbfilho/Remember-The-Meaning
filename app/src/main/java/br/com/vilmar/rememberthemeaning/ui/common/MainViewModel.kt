package br.com.vilmar.rememberthemeaning.ui.common

import android.arch.lifecycle.ViewModel
import br.com.vilmar.rememberthemeaning.ui.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    val uiEventLiveData = SingleLiveEvent<Int>()

    fun openSettings() {
        uiEventLiveData.value = OPEN_SETTINGS_SCREEN
    }

    fun openFeedback() {
        uiEventLiveData.value = OPEN_FEEDBACK_SCREEN
    }

    companion object {

        const val OPEN_SETTINGS_SCREEN = 1
        const val OPEN_FEEDBACK_SCREEN = 2

    }
}