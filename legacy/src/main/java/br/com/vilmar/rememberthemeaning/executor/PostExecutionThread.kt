package br.com.vilmar.rememberthemeaning.executor

import io.reactivex.Scheduler

interface PostExecutionThread {

    fun getScheduler() : Scheduler

}