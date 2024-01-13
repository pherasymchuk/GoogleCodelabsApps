package com.example.background.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

private const val TAG = "BlurWorker"

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        makeStatusNotification("Bluring image", appContext)

        return try {
            val picture: Bitmap =
                BitmapFactory.decodeResource(appContext.resources, R.drawable.android_cupcake)
            val bluredPicture = blurBitmap(picture, appContext)

            val outputUri: Uri = writeBitmapToFile(appContext, bluredPicture)

            makeStatusNotification("Output is ${outputUri}", appContext)

            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "doWork: Error applying blur")
            Result.failure()
        }
    }
}
