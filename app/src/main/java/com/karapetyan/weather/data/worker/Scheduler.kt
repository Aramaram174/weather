package com.karapetyan.weather.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleWorker(context: Context) {
    val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    val firstWork = OneTimeWorkRequestBuilder<WeatherWorker>()
        .setInitialDelay(1, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .addTag("WeatherWorkerTag")
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "WeatherWorkerUniqueName",
            ExistingWorkPolicy.REPLACE,
            firstWork
        )
}