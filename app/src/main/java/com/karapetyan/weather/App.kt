package com.karapetyan.weather

import android.app.Application
import com.karapetyan.weather.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import androidx.work.Configuration
import androidx.work.WorkerFactory
import org.koin.core.context.GlobalContext.get

class App : Application(), Configuration.Provider {

    private lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule, databaseModule, workerModule))
        }

        workerFactory = get().get()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}