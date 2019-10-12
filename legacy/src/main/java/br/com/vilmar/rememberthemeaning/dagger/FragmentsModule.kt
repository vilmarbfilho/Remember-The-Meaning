package br.com.vilmar.rememberthemeaning.dagger

import br.com.vilmar.rememberthemeaning.ui.deck.DeckFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun bindDeckFragment() : DeckFragment

}