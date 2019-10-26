package com.spartons.androiddownloadmanager

class PermissionsDelegate {

    fun request(listener: Listener) {

    }

    interface Listener {
        fun onPermissionGranted()
    }
}