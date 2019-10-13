package br.com.vilmar.rememberthemeaning.common

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private var observedValue: T? = null

    override fun onChanged(value: T?) {
        observedValue = value
    }

    fun getValue(): T? = observedValue
}