package com.example.bluromatic.data

import android.graphics.Bitmap
import androidx.annotation.WorkerThread

interface BlurredBitmap {
    @WorkerThread
    fun blur(): Bitmap

    /**
     * Creates an instance of [BlurredBitmap]
     * @param original Image to blur
     * @param blurRadius Blur radius (higher value means more blur)
     * @return Blurred bitmap image
     */
    class Default(
        private val original: Bitmap,
        private val blurRadius: Int,
    ) : BlurredBitmap {

        /**
         * Blurs the given Bitmap image using a
         * simple scaling technique.
         *
         * @return Blurred bitmap image
         */
        @WorkerThread
        override fun blur(): Bitmap {
            val scaleFactor = blurRadius * 5
            val scaledWidth = original.width / scaleFactor
            val scaledHeight = original.height / scaleFactor

            val input = Bitmap.createScaledBitmap(
                original,
                scaledWidth,
                scaledHeight,
                true
            )

            return Bitmap.createScaledBitmap(input, original.width, original.height, true)
        }
    }
}
