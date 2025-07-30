package com.karapetyan.weather.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import java.util.concurrent.TimeUnit
import kotlin.onSuccess

class WeatherWorker(context: Context, workerParams: WorkerParameters,
                       private val networkRepository: NetworkRepository,
                       private val repositoryRoom: WeatherRepositoryRoom) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            for (name in repositoryRoom.getAllCityNames()) {
                val result = networkRepository.getWeatherData(name)
                result.onSuccess {
                    repositoryRoom.updateCity(it)
                }.onFailure {
                    // show error toast
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
            Result.retry()
        }
    }
}