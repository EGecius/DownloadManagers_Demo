package com.egecius.downloads

import android.content.Context
import android.os.Environment
import java.io.File

@AllOpen
class DirectoryHelper(context: Context) {

    private val externalStorageForAndroidQ =
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == extStorageState
        }

    fun createDirectoryIfMissing() {
        if (isExternalStorageAvailable) {
            createDirectory()
        } else {
            // TODO: 2019-10-26 handle - external storage not available
        }
    }

    private fun createDirectory() {
        val directoryName = ROOT_DIRECTORY_NAME
        if (!isDirectoryExists(directoryName)) {
            val file = File(externalStorageForAndroidQ, directoryName)
            file.mkdir()
        }
    }

    private fun isDirectoryExists(@Suppress("SameParameterValue") directoryName: String): Boolean {
        val pathName = "$externalStorageForAndroidQ/$directoryName"
        val file = File(pathName)
        return file.isDirectory && file.exists()
    }

    companion object {
        const val ROOT_DIRECTORY_NAME = "DownloadManager"
    }
}