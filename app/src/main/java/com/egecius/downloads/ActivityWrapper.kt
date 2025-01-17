package com.egecius.downloads

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@AllOpen
class ActivityWrapper(private val appCompatActivity: AppCompatActivity) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermissionsExternalStorage() {
        appCompatActivity.requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    fun isPermissionGrantedExternalStorage(requestCode: Int, grantResults: IntArray): Boolean =
        requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED


    /** Is Android API at least 23 (Marshmallow 6.0) */
    fun isSdkAtLeastM() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    companion object {
        const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654
    }
}