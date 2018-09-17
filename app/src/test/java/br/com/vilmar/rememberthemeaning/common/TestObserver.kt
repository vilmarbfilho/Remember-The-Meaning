package br.com.vilmar.rememberthemeaning.common

import android.arch.lifecycle.Observer


class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }

}