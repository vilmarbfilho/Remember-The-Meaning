package br.com.vilmar.rememberthemeaning.common.extensions

import android.arch.lifecycle.MutableLiveData

fun MutableLiveData<Unit>.triggerEvent() {
    this.value = Unit
}

fun <T> MutableLiveData<T>.triggerEvent(value: T?) {
    this.value = value
}

fun <T> MutableLiveData<T>.triggerPostEvent(value: T?) {
    this.postValue(value)
}