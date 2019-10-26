package com.spartons.androiddownloadmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class PermissionsDelegate(private val activity: AppCompatActivity) {

    private val directoryHelper = DirectoryHelper(activity)
    private lateinit var listener: Listener

    fun requestPermission(listener: Listener) {
        this.listener = listener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askForPermission()
        } else {
            directoryHelper.createDirectoryIfMissing()
            listener.onPermissionGranted()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForPermission() {
        activity.requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                directoryHelper.createDirectoryIfMissing()
                listener.onPermissionGranted()
            }
        }
    }

    interface Listener {
        fun onPermissionGranted()
    }

    companion object {
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654
    }
}