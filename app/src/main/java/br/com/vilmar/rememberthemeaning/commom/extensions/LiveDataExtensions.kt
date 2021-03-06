package br.com.vilmar.rememberthemeaning.commom.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Unit>.triggerEvent() {
    this.value = Unit
}

fun <T> MutableLiveData<T>.triggerEvent(value: T?) {
    this.value = value
}

fun <T> MutableLiveData<T>.triggerPostEvent(value: T?) {
    this.postValue(value)
}