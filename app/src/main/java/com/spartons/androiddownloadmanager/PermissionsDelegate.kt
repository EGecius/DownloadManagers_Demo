package com.spartons.androiddownloadmanager

class PermissionsDelegate(
    private val directoryHelper: DirectoryHelper,
    private val activityWrapper: ActivityWrapper
) {

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