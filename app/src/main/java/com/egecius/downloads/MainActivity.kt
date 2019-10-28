package com.egecius.downloads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadmanagers_demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsDelegate: PermissionsDelegate
    private lateinit var networkDownloadsGateway: DownloadsGateway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsDelegate = PermissionsDelegate(
            DirectoryHelper(this),
            ActivityWrapper(this)
        )
        networkDownloadsGateway = NetworkDownloadsGateway(this)

        setClickListeners()
    }

    private fun setClickListeners() {
        downloadImageButton.setOnClickListener {
            requestDownloadImage()
        }
        downloadSongButton.setOnClickListener {
            requestDownloadSong()
        }
        downloadCsvButton.setOnClickListener {
            requestDownloadCsv()
        }
    }

    private fun requestDownloadImage() {
        permissionsDelegate.requestPermission(object : PermissionsDelegate.Listener {
            override fun onPermissionGranted() {
                networkDownloadsGateway.download(PDF_DOWNLOAD_PATH)
            }
        })
    }

    private fun requestDownloadSong() {
        permissionsDelegate.requestPermission(object : PermissionsDelegate.Listener {
            override fun onPermissionGranted() {
                networkDownloadsGateway.download(SONG_DOWNLOAD_PATH)
            }
        })
    }

    private fun requestDownloadCsv() {
        permissionsDelegate.requestPermission(object : PermissionsDelegate.Listener {
            override fun onPermissionGranted() {
                networkDownloadsGateway.download(CSV_DOWNLOAD_PATH)
            }
        })
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
        private const val CSV_DOWNLOAD_PATH =
            "https://samplecsvs.s3.amazonaws.com/Sacramentorealestatetransactions.csv"
    }
}
