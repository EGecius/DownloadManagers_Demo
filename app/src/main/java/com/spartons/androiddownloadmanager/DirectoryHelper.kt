package com.spartons.androiddownloadmanager

import android.content.Context
import android.os.Environment
import java.io.File


class DirectoryHelper(context: Context) {

    private val externalStorageForAndroidQ =
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == extStorageState
        }

    fun createDirectory() {
        if (isExternalStorageAvailable)
            createDirectory(ROOT_DIRECTORY_NAME)
    }

    private fun createDirectory(directoryName: String) {
        if (!isDirectoryExists(directoryName)) {
            val file = File(externalStorageForAndroidQ, directoryName)
            file.mkdir()
        }
    }

    private fun isDirectoryExists(directoryName: String): Boolean {
        val file = File("$externalStorageForAndroidQ/$directoryName")
        return file.isDirectory && file.exists()
    }

    companion object {

        const val ROOT_DIRECTORY_NAME = "DownloadManager"

    }
}