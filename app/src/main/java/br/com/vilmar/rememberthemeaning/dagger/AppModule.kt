package br.com.vilmar.rememberthemeaning.dagger

import android.content.Context
import br.com.vilmar.rememberthemeaning.RememberTheMeaningApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication(rtmApplication : RememberTheMeaningApplication): Context = rtmApplication

}