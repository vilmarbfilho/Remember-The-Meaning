package br.com.vilmar.rememberthemeaning.di

import br.com.vilmar.rememberthemeaning.ui.deck.DeckViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { DeckViewModel(get(), get(), get(), get()) }
}