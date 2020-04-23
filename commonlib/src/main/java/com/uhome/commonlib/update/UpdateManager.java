package com.uhome.commonlib.update;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.uhome.commonlib.bean.UpdateEntity;

import java.io.File;
import java.util.concurrent.Executors;

import cn.pompip.okhttplib.Net;
import cn.pompip.okhttplib.NetCallback;
import cn.pompip.okhttplib.NetResult;

public class UpdateManager {
    private static final String api_token = "1f2ccea22cb6ea39ef7795624ca57fd1";
    private static final String TAG = "UpdateManager";
    private String appId = "5e4fbc22f94548462de1b0e4";
    private int localVersionCode;
    private UpdateEntity updateEntity;


    private static class Inner {
        private static UpdateManager updateManager = new UpdateManager();
    }

    public static UpdateManager getInstance() {
        return Inner.updateManager;
    }


    public void start(final Context context, String appId, int localVersionCode) {
        this.appId = appId;
        this.localVersionCode = 7;
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                check(context);
            }
        });
    }

    public void check(final Context context) {
        Net.url("http://api.bq04.com/apps/latest/" + appId + "?api_token=" + api_token).execute(new NetCallback() {
            @Override
            public void result(NetResult result) {
                if (!result.isSuccess) {
                    return;
                }
                UpdateEntity entity = result.json(UpdateEntity.class);

                Log.d(TAG, "success: " + entity.build);
                Log.d(TAG, "success: " + entity.installUrl);
                if (TextUtils.isEmpty(entity.name) || TextUtils.isEmpty(entity.versionShort) || TextUtils.isEmpty(entity.build)) {
                    return;
                }
                updateEntity = entity;
                if (isNeedUpdate()) {
                    if (isDownloadFileExist()) {
                        InstallManager.install(context, getDownloadFile());
                    } else {
                        new DownloadUtil(context, getDownloadFileName(), entity.installUrl).download();
                    }
                }
            }
        });

    }

    public boolean isNeedUpdate() {
        if (localVersionCode == 0) {
            return false;
        }
        String build = updateEntity.build;
        try {
            int remoteVersionCode = Integer.parseInt(build);
            return remoteVersionCode > localVersionCode;
        } catch (Exception e) {
            return false;
        }
    }


    private String getDownloadFileName() {
        String downloadFileName = updateEntity.name + "_" + updateEntity.versionShort + "." + updateEntity.build + ".apk";
        return downloadFileName;
    }

    private File getDownloadFile( ){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getDownloadFileName());
        return file;
    }

    boolean isDownloadFileExist( ) {
        return getDownloadFile().exists();
    }
}
