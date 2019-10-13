package br.com.vilmar.rememberthemeaning

import br.com.vilmar.rememberthemeaning.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RTMApplication: RememberTheMeaningApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RTMApplication)
            modules(listOf(
                viewModelModule
            ))
        }
    }
}