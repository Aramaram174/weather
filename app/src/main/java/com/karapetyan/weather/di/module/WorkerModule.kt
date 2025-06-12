package com.karapetyan.weather.di.module

import androidx.work.WorkerFactory
import org.koin.dsl.module

val workerModule = module {
    single<WorkerFactory>{ KoinWorkerFactory(get(), get()) }
}