package com.mzaragozaserrano.idealista.presentation.screens

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.captureToBitmap
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mzaragozaserrano.presentation.activities.IdealistaActivity
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.io.File
import java.io.OutputStream

@RunWith(AndroidJUnit4::class)
class HomeScreenScreenshotTest {

    private lateinit var idlingResource: IdlingResource

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(IdealistaActivity::class.java)

    @get:Rule(order = 2)
    val nameRule = TestName()

    @Before
    fun setup() {
        idlingResource = MyIdlingResource()
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun captureHomeScreen() {
        Thread.sleep(3000)
        onView(isRoot()).perform(captureToBitmap { bitmap -> saveBitmapToInternalStorage(bitmap = bitmap) })
    }

    @Test
    fun checkHomeScreen() {
        Thread.sleep(3000)
        onView(isRoot()).perform(
            captureToBitmap { bitmap ->
                val expected = loadBitmapFromStorage(displayName = "ContentValues_captureHomeScreen.png")
                assertTrue(bitmap.sameAs(expected))
            }
        )
    }

    private fun saveBitmapToInternalStorage(bitmap: Bitmap) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "${javaClass.simpleName}_${nameRule.methodName}.png"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            uri?.let {
                val outputStream: OutputStream? = context.contentResolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
            }
        } else {
            val file = File(
                context.getExternalFilesDir(null),
                "${javaClass.simpleName}_${System.currentTimeMillis()}.png"
            )
            val outputStream = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        }
    }

    private fun loadBitmapFromStorage(displayName: String): Bitmap? {
        val context = ApplicationProvider.getApplicationContext<Context>()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(displayName)

            context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val imageUri = ContentUris.withAppendedId(uri, id)
                    return context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }
                }
            }
            null
        } else {
            val file = File(context.getExternalFilesDir(null), displayName)
            if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
        }
    }


}

class MyIdlingResource : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = "MyIdlingResource"

    override fun isIdleNow(): Boolean {
        val isIdle = true
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}

fun waitForIdle(): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription(): String = "Esperar a que la UI esté inactiva"

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
        }
    }
}