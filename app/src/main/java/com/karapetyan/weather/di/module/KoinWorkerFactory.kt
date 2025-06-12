package com.karapetyan.weather.di.module

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import com.karapetyan.weather.data.worker.WeatherWorker

class KoinWorkerFactory(
    private val repository: NetworkRepository,
    private val repositoryRoom: WeatherRepositoryRoom
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            WeatherWorker::class.java.name -> {
                WeatherWorker(appContext, workerParameters, repository, repositoryRoom)
            }

            else -> {
                Log.e("KARAPETYAN", "Unknown worker: $workerClassName")
                null
            }
        }
    }
}