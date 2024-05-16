package com.norm.aicameraattractions.data.repository

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.norm.aicameraattractions.R
import com.norm.aicameraattractions.model.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val application: Application,
) : CameraRepository {
    override suspend fun takePhoto(controller: LifecycleCameraController) {
        controller.takePicture(
            ContextCompat.getMainExecutor(application),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply {
                        postRotate(
                            image.imageInfo.rotationDegrees.toFloat()
                        )
                    }
                    val imageBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0, 0,
                        image.width, image.height,
                        matrix, true,
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        savePhoto(imageBitmap)
                    }
                }
            }
        )
    }

    private suspend fun savePhoto(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val resolver: ContentResolver = application.contentResolver

            val imageCollection = MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            val appName = application.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()
            val imageContentValues: ContentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "${timeInMillis}_iamge" + ".jpg",

                    )
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/$appName"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val imageMediaStoreUri: Uri? = resolver.insert(
                imageCollection, imageContentValues,
            )

            imageMediaStoreUri?.let { uri ->
                try {
                    resolver.openOutputStream(uri)?.let { outputStream ->
                        bitmap.compress(
                            Bitmap.CompressFormat.JPEG, 100, outputStream,
                        )
                    }

                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(
                        uri, imageContentValues, null, null,
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    resolver.delete(uri, null, null)
                }
            }
        }
    }
}
