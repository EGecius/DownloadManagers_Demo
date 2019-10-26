package com.spartons.androiddownloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri

class NetworkExportsGateway(
    context: Context,
    private val destinationPath: String
) : ExportsGateway {

    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun download(downloadUrl: String) {
        val uri = Uri.parse(downloadUrl)
        val request = createRequest(uri)
        downloadManager.enqueue(request)
    }

    private fun createRequest(uri: Uri): DownloadManager.Request {
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle("Downloading a file")
        request.setDestinationInExternalPublicDir(destinationPath, uri.lastPathSegment)
        return request
    }
}
