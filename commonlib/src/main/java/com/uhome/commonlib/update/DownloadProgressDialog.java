package com.uhome.commonlib.update;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uhome.commonlib.R;

import java.util.logging.LogRecord;

public class DownloadProgressDialog {

    static class Inner {
        static DownloadProgressDialog downloadProgressDialog = new DownloadProgressDialog();
    }

    public static DownloadProgressDialog getInstance() {
        return Inner.downloadProgressDialog;
    }


    private AlertDialog alertDialog;
    Handler handler = new Handler(Looper.getMainLooper());

    public void showDialog(final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(R.layout.view_progress).setCancelable(false);

                alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    public void dismiss(){
        if (alertDialog!=null&&alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    public void update(final double total, final double download) {
        if (alertDialog!=null&&alertDialog.isShowing()) {


            handler.post(new Runnable() {
                @Override
                public void run() {
                    ProgressBar progress_update = alertDialog.findViewById(R.id.progress_update);
                    TextView txt_update = alertDialog.findViewById(R.id.txt_update);
                    int progress = (int) (download * 100 / total);
                    txt_update.setText(progress + "%");
                    progress_update.setProgress(progress);
                }
            });

        }
    }
}
