package com.karapetyan.weather.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import java.util.concurrent.TimeUnit
import com.karapetyan.weather.data.repository.Result

class WeatherWorker(context: Context, workerParams: WorkerParameters,
                       private val networkRepository: NetworkRepository,
                       private val repositoryRoom: WeatherRepositoryRoom) : CoroutineWorker(context, workerParams) {

    init {
        Log.d("KARAPETYAN", "WeatherWorker: constructor called")
    }

    override suspend fun doWork(): Result {
        return try {
            for (name in repositoryRoom.getAllCityNames()) {
                when (val result = networkRepository.getWeatherData(name)) {
                    is com.karapetyan.weather.data.repository.Result.Success -> {
                        repositoryRoom.updateCity(result.data)
                    }
                    is Result.Error -> {}
                }
            }

            val nextWork = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork("WeatherWorkerUniqueName",
                ExistingWorkPolicy.REPLACE, nextWork)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("KARAPETYAN", "doWork ERROR", e)
            Result.retry()
        }
    }
}