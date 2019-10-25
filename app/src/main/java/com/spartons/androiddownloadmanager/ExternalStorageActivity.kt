package com.spartons.androiddownloadmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadmanagers_demo.R
import com.spartons.androiddownloadmanager.DirectoryHelper.ROOT_DIRECTORY_NAME
import com.spartons.androiddownloadmanager.DirectoryHelper.createDirectory
import com.spartons.androiddownloadmanager.DownloadSongService.getDownloadService

class ExternalStorageActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.downloadImageButton).setOnClickListener(this)
        findViewById<View>(R.id.downloadSongButton).setOnClickListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST_CODE
            )
            return
        }
        createDirectory(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.downloadImageButton -> {
                startService(getDownloadService(this, PDF_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
            }
            R.id.downloadSongButton -> {
                startService(getDownloadService(this, SONG_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                createDirectory(this)
        }
    }

    companion object {

        private val PDF_DOWNLOAD_PATH =
            "https://www.goldmansachs.com/insights/pages/gs-research/taking-the-heat/report.pdf"
        private val SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download"
        private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654
    }
}
