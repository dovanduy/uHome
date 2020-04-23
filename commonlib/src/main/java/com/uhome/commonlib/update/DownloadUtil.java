package com.uhome.commonlib.update;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileDescriptor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DownloadUtil {
    private static final String TAG = "DownloadUtil";
    private String fileName;
    private String downLoadUrl;
    private Context context;
    private long downloadId;
    private DownloadManager downloadManager;
    ScheduledExecutorService scheduledExecutorService;

    public DownloadUtil(Context context, String fileName, String downLoadUrl) {
        this.fileName = fileName;
        this.downLoadUrl = downLoadUrl;
        this.context = context;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void download() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        DownloadProgressDialog.getInstance().showDialog(context);



        register();
        if (downloadManager != null) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("application/vnd.android.package-archive");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setTitle(fileName);
            request.setDescription("下载中");
            downloadId = downloadManager.enqueue(request);
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    queryDownload();
                }
            },0,100,TimeUnit.MILLISECONDS);
        }
    }

    public void queryDownload() {

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        try (Cursor cursor = downloadManager.query(query)) {
            if (cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
                int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int urlIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                int totalSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int downloadSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

                String title = cursor.getString(titleIndex);
                long totalSize = cursor.getLong(totalSizeIndex);
                long downloadSize = cursor.getLong(downloadSizeIndex);
                int status = cursor.getInt(statusIndex);
                String localUrl = cursor.getString(urlIndex);

                Log.d(TAG, "queryDownload() called" + title + " " + totalSize + " " + downloadSize + " " + localUrl);
                switch (status) {
                    case DownloadManager.STATUS_RUNNING:
                        DownloadProgressDialog.getInstance().update(totalSize, downloadSize);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        DownloadProgressDialog.getInstance().dismiss();
                        unregister();
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        InstallManager.install(context, file);
                        break;
                    case DownloadManager.STATUS_FAILED:

                    default:
                        unregister();
                        break;
                }
                cursor.close();
            }
        }

    }

    private CompleteReceiver completeReceiver;

    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (completeDownloadId == downloadId) {
                Uri uriForDownloadedFile = downloadManager.getUriForDownloadedFile(completeDownloadId);
                InstallManager.install(context, uriForDownloadedFile);

            }
        }
    }

    ;

    private void register() {
        completeReceiver = new CompleteReceiver();
        context.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    private void unregister() {
        if (completeReceiver != null) {
            context.unregisterReceiver(completeReceiver);

        }
        scheduledExecutorService.shutdown();
    }
}
