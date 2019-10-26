package com.spartons.androiddownloadmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadmanagers_demo.R
import com.spartons.androiddownloadmanager.DirectoryHelper.Companion.ROOT_DIRECTORY_NAME
import com.spartons.androiddownloadmanager.DirectoryHelper.Companion.createDirectory
import com.spartons.androiddownloadmanager.DownloadSongService.getDownloadService
import kotlinx.android.synthetic.main.activity_main.*

class ExternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
        requestExternalDirectory()
    }

    private fun setListeners() {
        downloadImageButton.setOnClickListener {
            startService(getDownloadService(this, PDF_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
        }
        downloadSongButton.setOnClickListener {
            startService(getDownloadService(this, SONG_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
        }
    }

    private fun requestExternalDirectory() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askForPermission()
        } else {
            createDirectory(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askForPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
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
        private const val PDF_DOWNLOAD_PATH =
            "https://www.goldmansachs.com/insights/pages/gs-research/taking-the-heat/report.pdf"
        private const val SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download"
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654
    }
}
