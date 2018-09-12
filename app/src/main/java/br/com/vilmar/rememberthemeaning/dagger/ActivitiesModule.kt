package br.com.vilmar.rememberthemeaning.dagger

import br.com.vilmar.rememberthemeaning.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}