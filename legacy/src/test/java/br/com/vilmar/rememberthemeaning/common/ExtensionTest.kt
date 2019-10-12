package br.com.vilmar.rememberthemeaning.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert.fail

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}

fun <T> LiveData<T>.verify(body: ((T?) -> Unit)? = null) {
    val latch = java.util.concurrent.CountDownLatch(1)

    var verify = false

    var observer: Observer<T>? = null

    observer = Observer {
        latch.countDown()
        verify = true
        this.removeObserver(observer!!)
        body?.invoke(this.value)
    }

    this.observeForever(observer)

    latch.await(2, java.util.concurrent.TimeUnit.SECONDS)

    if (!verify) {
        fail("LiveData observer not triggered")
    }
}
