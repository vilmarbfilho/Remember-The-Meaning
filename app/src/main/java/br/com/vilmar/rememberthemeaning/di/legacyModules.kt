package br.com.vilmar.rememberthemeaning.di

import br.com.vilmar.rememberthemeaning.data.database.dao.VocabularyDao
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyDataSource
import br.com.vilmar.rememberthemeaning.data.repository.VocabularyRepository
import br.com.vilmar.rememberthemeaning.executor.JobExecutor
import br.com.vilmar.rememberthemeaning.executor.PostExecutionThread
import br.com.vilmar.rememberthemeaning.executor.ThreadExecutor
import br.com.vilmar.rememberthemeaning.executor.UIThread
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// Temporary module
// This module will be removed after refactor of legacy base code

val legacyModule = module {
    single { CompositeDisposable() }

    single { VocabularyRepository(get()) }

    single<VocabularyDataSource> { VocabularyDao(androidContext()) }

    single<ThreadExecutor> { JobExecutor() }

    single<PostExecutionThread> { UIThread() }
}