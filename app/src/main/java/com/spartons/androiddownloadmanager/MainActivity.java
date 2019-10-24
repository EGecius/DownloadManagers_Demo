package com.spartons.androiddownloadmanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.downloadmanagers_demo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IMAGE_DOWNLOAD_PATH = "http://globalmedicalco.com/photos/globalmedicalco/9/41427.jpg";
    private static final String SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.downloadImageButton).setOnClickListener(this);
        findViewById(R.id.downloadSongButton).setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return;
        }
        com.spartons.androiddownloadmanager.DirectoryHelper.createDirectory(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadImageButton: {
                startService(com.spartons.androiddownloadmanager.DownloadSongService.getDownloadService(this, IMAGE_DOWNLOAD_PATH, com.spartons.androiddownloadmanager.DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                break;
            }
            case R.id.downloadSongButton: {
                startService(com.spartons.androiddownloadmanager.DownloadSongService.getDownloadService(this, SONG_DOWNLOAD_PATH, com.spartons.androiddownloadmanager.DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                com.spartons.androiddownloadmanager.DirectoryHelper.createDirectory(this);
        }
    }
}
