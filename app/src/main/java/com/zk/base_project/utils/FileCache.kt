package com.zk.base_project.utils

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.zk.base_project.App
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

object FileCache {

    // content resolver
    private val contentResolver = App.context.contentResolver

    // to get the type of file
    private val mimeTypeMap = MimeTypeMap.getSingleton()

    private val mCacheLocation = App.context.cacheDir

    fun cacheThis(uri: Uri): File {
//        executor.submit {
             return copyFromSource(uri)
//        }
    }

    /**
     * Copies the actual data from provided content provider.
     */
    private fun copyFromSource(uri: Uri): File {

        val fileExtension: String = getFileExtension(uri) ?: kotlin.run {
            throw RuntimeException("Extension is null for $uri")
        }
        val fileName = queryName(uri) ?: getFileName(fileExtension)

        val inputStream = contentResolver.openInputStream(uri) ?: kotlin.run {
            throw RuntimeException("Cannot open for reading $uri")
        }
        val bufferedInputStream = BufferedInputStream(inputStream)

        // the file which will be the new cached file
        val outputFile = File(mCacheLocation, fileName)
        val bufferedOutputStream = BufferedOutputStream(FileOutputStream(outputFile))

        // this will hold the content for each iteration
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

        var readBytes = 0 // will be -1 if reached the end of file

        while (true) {
            readBytes = bufferedInputStream.read(buffer)

            // check if the read was failure
            if (readBytes == -1) {
                bufferedOutputStream.flush()
                break
            }

            bufferedOutputStream.write(buffer)
            bufferedOutputStream.flush()
        }

        // close everything
        inputStream.close()
        bufferedInputStream.close()
        bufferedOutputStream.close()

        return outputFile

    }

    private fun getFileExtension(uri: Uri): String? {
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    /**
     * Tries to get actual name of the file being copied.
     * This might be required in some of the cases where you might want to know the file name too.
     *
     * @param uri
     *
     */
    @SuppressLint("Recycle")
    private fun queryName(uri: Uri): String? {
        val returnCursor: Cursor = contentResolver.query(uri, null, null, null, null) ?: return null
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    private fun getFileName(fileExtension: String): String {
        return "${System.currentTimeMillis().toString()}.$fileExtension"
    }

    /**
     * Remove everything that we have cached.
     * You might want to invoke this method before quiting the application.
     */
    fun removeAll() {
        App.context.cacheDir.deleteRecursively()
    }

    // base buffer size
    private const val BASE_BUFFER_SIZE = 1024

    // if you want to modify size use binary multiplier 2, 4, 6, 8
    private const val DEFAULT_BUFFER_SIZE = BASE_BUFFER_SIZE * 4

    private val executor = Executors.newSingleThreadExecutor()

}