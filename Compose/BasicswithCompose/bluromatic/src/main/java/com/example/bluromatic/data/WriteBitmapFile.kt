package com.example.bluromatic.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.bluromatic.OUTPUT_PATH
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.UUID

interface WriteBitmapFile {
    fun write(): Uri

    class Default(
        private val context: Context,
        private val bitmap: Bitmap,
        private val name: String = "blur-filter-output-${UUID.randomUUID()}.png",
        private val outputDir: String = OUTPUT_PATH,
    ) : WriteBitmapFile {
        /**
         * Writes bitmap to a temporary file and returns the Uri for the file
         *  @return Uri for temp file with bitmap
         */
        @Throws(FileNotFoundException::class)
        override fun write(): Uri {
            val outputDir = File(context.filesDir, outputDir)
            if (!outputDir.exists()) {
                outputDir.mkdirs() // should succeed
            }
            val outputFile = File(outputDir, name)

            FileOutputStream(outputFile).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, it)
            }

            return Uri.fromFile(outputFile)
        }
    }
}
