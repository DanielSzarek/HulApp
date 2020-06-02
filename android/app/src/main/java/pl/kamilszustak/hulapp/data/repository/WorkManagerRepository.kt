package pl.kamilszustak.hulapp.data.repository

import android.app.Application
import androidx.work.WorkManager

abstract class WorkManagerRepository(application: Application) {
    val workManager: WorkManager = WorkManager.getInstance(application)
}