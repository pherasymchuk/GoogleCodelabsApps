/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.waterme.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

interface PlantsRepository {
    val plants: List<Plant>
    fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String)

    class Default(context: Context) : PlantsRepository {
        private val workManager = WorkManager.getInstance(context)
        override val plants: List<Plant> = DataSource.plants

        override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
            val inputData = workDataOf(WaterReminderWorker.PLANT_NAME_KEY to plantName)
            val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
                .setInitialDelay(duration, unit)
                .addTag(plantName)
                .setInputData(inputData)
                .build()
            workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }

        companion object {
            const val WORK_NAME = "schedule_reminder"
        }
    }
}
