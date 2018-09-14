package br.com.vilmar.rememberthemeaning.common

import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}