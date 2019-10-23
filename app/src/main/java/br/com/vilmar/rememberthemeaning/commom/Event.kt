package br.com.vilmar.rememberthemeaning.ui

import java.util.concurrent.atomic.AtomicBoolean

open class Event<out T>(private val content: T) {

    var hasBeenHandled = AtomicBoolean(false)
        private set

    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled.compareAndSet(true, true)) {
            null
        } else{
            content
        }

        /*return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }*/
    }

    fun peekContent(): T = content
}
