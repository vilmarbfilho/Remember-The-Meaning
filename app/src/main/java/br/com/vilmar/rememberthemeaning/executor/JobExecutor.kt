package br.com.vilmar.rememberthemeaning.executor

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {

    private val threadPoolExecutor : ThreadPoolExecutor

    init {
        threadPoolExecutor = ThreadPoolExecutor(
                3,
                5,
                10,
                TimeUnit.SECONDS,
                LinkedBlockingQueue(), JobThreadFactory())
    }

    override fun execute(runnable: Runnable) {
        threadPoolExecutor.execute(runnable)
    }

    class JobThreadFactory : ThreadFactory {

        private var counter : Int = 0

        override fun newThread(runnable: Runnable): Thread {
           return Thread(runnable, "android_${counter++}")
        }
    }

}