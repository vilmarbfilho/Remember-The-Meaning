package br.com.vilmar.rememberthemeaning.dagger

import br.com.vilmar.rememberthemeaning.executor.JobExecutor
import br.com.vilmar.rememberthemeaning.executor.PostExecutionThread
import br.com.vilmar.rememberthemeaning.executor.ThreadExecutor
import br.com.vilmar.rememberthemeaning.executor.UIThread
import dagger.Module
import dagger.Provides

@Module
class ThreadModule {

    @Provides
    fun providesJobExecutor() : ThreadExecutor = JobExecutor()

    @Provides
    fun providesUIThread() : PostExecutionThread = UIThread()

}