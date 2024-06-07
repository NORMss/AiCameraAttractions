package com.norm.aicameraattractions.presentation.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.text.Text
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class ImageWidget : GlanceAppWidget() {

    val Context.imageWidgetStore by preferencesDataStore("ImageWidget")
    private val lastImage = stringPreferencesKey("lastImage")

    suspend fun DataStore<Preferences>.loadImage() {
        updateData { prefs ->
            prefs.toMutablePreferences().apply {
                this[lastImage] =
                    "file:///storage/emulated/0/DCIM/Camera/IMG_20240531_171854407.jpg"
            }
        }
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        coroutineScope {
            val store = context.imageWidgetStore
            val currentImage = store.data
                .map { prefs ->
                    prefs[lastImage]
                }
                .stateIn(this@coroutineScope)
            if (currentImage.value == null) store.loadImage()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "weatherWidgetWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequest.Builder(
                    ImageWidgetWorker::class.java,
                    15.minutes.toJavaDuration()
                ).setInitialDelay(15.minutes.toJavaDuration()).build()
            )

            provideContent {
                val imagePath by currentImage.collectAsState()
                Text(
                    text = imagePath.toString()
                )
            }
        }
    }
}

class ImageWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = ImageWidget()
}

class ImageWidgetWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        ImageWidget().apply {
            applicationContext.imageWidgetStore.loadImage()
            updateAll(applicationContext)
        }
        return Result.success()
    }
}