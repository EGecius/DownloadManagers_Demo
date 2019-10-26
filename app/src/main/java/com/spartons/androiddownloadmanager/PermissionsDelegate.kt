package com.spartons.androiddownloadmanager

import androidx.appcompat.app.AppCompatActivity

class PermissionsDelegate(activity: AppCompatActivity) {

    private val directoryHelper = DirectoryHelper(activity)
    private val activityWrapper = ActivityWrapper(activity)

    private lateinit var listener: Listener

    fun requestPermission(listener: Listener) {
        this.listener = listener
        if (activityWrapper.isBuildVersionCodeM()) {
            activityWrapper.requestPermissionsExternalStorage()
        } else {
            directoryHelper.createDirectoryIfMissing()
            listener.onPermissionGranted()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (activityWrapper.isPermissionGrantedExternalStorage(requestCode, grantResults)) {
            directoryHelper.createDirectoryIfMissing()
            listener.onPermissionGranted()
        }
    }

    interface Listener {
        fun onPermissionGranted()
    }

}