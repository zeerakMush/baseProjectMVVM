package com.zk.base_project.utils

import android.Manifest
import android.net.Uri
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File

class FilePicker(
    private val caller: ActivityResultCaller,
    private val permissionHelper: PermissionHelper
) {

    private var capturedImageUri: Uri? = null
    private var onResult: ((File?) -> Unit)? = null

    private var getContent =
        caller.registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null)
            onResult?.invoke(FileCache.cacheThis(it))
        }

    private var takePicture =
        caller.registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val temp = capturedImageUri
            if (it && temp != null) {
                onResult?.invoke(FileCache.cacheThis(temp))
            }
        }

    fun captureImage(onCapture: (File?) -> Unit) {
        val uri = Utils.getFileUri(Utils.getImageCaptureFile())
        permissionHelper.check(listOf(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE)) {
            this.capturedImageUri = uri
            this.onResult = onCapture
            takePicture.launch(uri)
        }
    }

    fun selectImage(onImageSelected: (File?) -> Unit) {
        permissionHelper.check(listOf(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            this.onResult = onImageSelected
            getContent.launch("image/*")
        }
    }

    fun selectFile(fileType: FileType, onFileSelected: (File?) -> Unit) {
        permissionHelper.check(listOf(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            this.onResult = onFileSelected
            getContent.launch(fileType.mime)
        }
    }

    sealed class FileType(var mime: String) {
        object PDF : FileType("application/pdf")
    }
}