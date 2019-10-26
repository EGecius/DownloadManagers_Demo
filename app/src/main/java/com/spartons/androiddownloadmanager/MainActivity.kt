package com.spartons.androiddownloadmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadmanagers_demo.R
import com.spartons.androiddownloadmanager.DirectoryHelper.Companion.ROOT_DIRECTORY_NAME
import com.spartons.androiddownloadmanager.DownloadSongService.getDownloadService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsDelegate: PermissionsDelegate
    private lateinit var networkDownloadsGateway: DownloadsGateway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsDelegate = PermissionsDelegate(DirectoryHelper(this), ActivityWrapper(this))
        networkDownloadsGateway = NetworkDownloadsGateway(this)

        setListeners()
    }

    private fun setListeners() {
        downloadImageButton.setOnClickListener {
            requestDownloadImage()
        }
        downloadSongButton.setOnClickListener {
            requestDownloadSong()
        }
    }

    private fun requestDownloadImage() {
        permissionsDelegate.requestPermission(object : PermissionsDelegate.Listener {
            override fun onPermissionGranted() {
                doDownloadImage()
            }
        })
    }

    private fun doDownloadImage() {
        startService(getDownloadService(this, PDF_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
    }

    private fun requestDownloadSong() {
        permissionsDelegate.requestPermission(object : PermissionsDelegate.Listener {
            override fun onPermissionGranted() {
                doDownloadSong()
            }
        })
    }

    private fun doDownloadSong() {
        startService(getDownloadService(this, SONG_DOWNLOAD_PATH, "$ROOT_DIRECTORY_NAME/"))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsDelegate.onRequestPermissionsResult(requestCode, grantResults)
    }


    companion object {
        private const val PDF_DOWNLOAD_PATH =
            "https://www.goldmansachs.com/insights/pages/gs-research/taking-the-heat/report.pdf"
        private const val SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download"
    }
}
