package com.example.bluromatic

import android.content.ContentResolver
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class ImageUriTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun uri_isDefaultDrawable() {
        val expectedUri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(appContext.resources.getResourcePackageName(R.drawable.android_cupcake))
            .appendPath(appContext.resources.getResourceTypeName(R.drawable.android_cupcake))
            .appendPath(appContext.resources.getResourceEntryName(R.drawable.android_cupcake))
            .build()

        val imageUri = ImageUri.Drawable(appContext)
        Assert.assertEquals(expectedUri, imageUri.uri())
    }

    @Test
    fun uri_isCustomDrawable() {
        val customDrawableId: Int = R.drawable.ic_launcher_background
        val expectedUri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(appContext.resources.getResourcePackageName(customDrawableId))
            .appendPath(appContext.resources.getResourceTypeName(customDrawableId))
            .appendPath(appContext.resources.getResourceEntryName(customDrawableId))
            .build()

        val imageUri = ImageUri.Drawable(appContext, customDrawableId)
        Assert.assertEquals(expectedUri, imageUri.uri())
    }
}
