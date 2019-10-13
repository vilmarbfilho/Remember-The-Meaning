package br.com.vilmar.rememberthemeaning.dagger

import br.com.vilmar.rememberthemeaning.RememberTheMeaningApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = arrayOf(
        AppModule::class,
        AndroidSupportInjectionModule::class,
        DaoModule::class,
        DataRepositoryModule::class,
        ThreadModule::class
))
interface ApplicationComponent {

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun application(application: RememberTheMeaningApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(rtmApplication: RememberTheMeaningApplication)

}