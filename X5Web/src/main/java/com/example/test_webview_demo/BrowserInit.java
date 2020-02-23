package com.example.test_webview_demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

public class BrowserInit {
    public static void init(final Context context, final InitListener initListener){
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(context.getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("app", " onViewInitFinished is " + b);
                if (b){
                    initListener.callback(true,QbSdk.getTbsVersion(context)+"");
                }else{
                    initListener.callback(false,"0");
                }
            }
        });
    }

    public static interface InitListener{
        void callback(boolean isSuccess,String version);
    }
}
