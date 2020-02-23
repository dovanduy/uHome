package cn.pompip.crosswalklib;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class CrossWalkActivity extends XWalkActivity {
    XWalkView xWalkView;
    private boolean isReady = false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (xWalkView != null) {
            xWalkView.onNewIntent(intent);
        }
    }

    String app_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crosswalk);
        xWalkView = findViewById(R.id.xwalkview);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ViewGroup.LayoutParams layoutParams = xWalkView.getLayoutParams();
        layoutParams.height = point.x;
        layoutParams.width = point.y * 1280 / 752;
        xWalkView.setLayoutParams(layoutParams);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        if (app_name == null) {
            Intent intent = getIntent();
            app_name = intent.getStringExtra("app_name");
//			app_name =intent.getData().toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, false);
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (xWalkView != null && isReady) {
            xWalkView.resumeTimers();
            xWalkView.onShow();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkView != null && isReady) {
            xWalkView.pauseTimers();
            xWalkView.onHide();
        }
    }

//    private static final String TAG = "CrossWalkActivity";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onXWalkReady() {
        XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);   //开启默认动画
        XWalkSettings setting = xWalkView.getSettings();
        setting.setLoadWithOverviewMode(false);
        setting.setJavaScriptEnabled(true);               //支持js
        setting.setJavaScriptCanOpenWindowsAutomatically(true);       //支持通过JS打开新窗口
        setting.setUseWideViewPort(true);                          //将图片调整到合适webview的大小
        setting.setLoadWithOverviewMode(true);                        //缩放至屏幕的大小
        setting.setLoadsImagesAutomatically(true);                    //支持自动加载图片
        setting.supportMultipleWindows();                   //支持多窗口
        setting.setSupportZoom(true);
        setting.setAllowFileAccess(true);
        setting.setDomStorageEnabled(true);
        setting.setAllowContentAccess(true);
        setting.setAllowContentAccess(true);
        setting.setDomStorageEnabled(true);
        xWalkView.requestFocus();
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        xWalkView.setResourceClient(new XWalkResourceClient(xWalkView) {
            @Override
            public void onLoadStarted(XWalkView view, String url) {
                super.onLoadStarted(view, url);
//                Log.d(TAG, "onLoadStarted() called with: view = [" + view + "], url = [" + url + "]");
            }

            @Override
            public void onLoadFinished(XWalkView view, String url) {
                super.onLoadFinished(view, url);
//                Log.d(TAG, "onLoadFinished() called with: view = [" + view + "], url = [" + url + "]");
            }

            @Override
            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
                view.loadUrl(url);
//                Log.d(TAG, "shouldOverrideUrlLoading() called with: view = [" + view + "], url = [" + url + "]");
                return true;
            }

            @Override
            public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
                callback.onReceiveValue(true);
                super.onReceivedSslError(view, callback, error);
            }


            @Override
            public void onReceivedLoadError(XWalkView view, int errorCode,
                                            String description, String failingUrl) {
                super.onReceivedLoadError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onProgressChanged(XWalkView view, int process) {
                super.onProgressChanged(view, process);

            }
        });
        xWalkView.setUIClient(new XWalkUIClient(xWalkView) {

            @Override
            public boolean onJsAlert(XWalkView view, String url,
                                     String message, XWalkJavascriptResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(XWalkView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                super.openFileChooser(view, uploadFile, acceptType, capture);
            }
        });

        xWalkView.loadUrl(app_name);
        isReady = true;

    }

    @Override
    public void onBackPressed() {
        if (xWalkView.getNavigationHistory().canGoBack()) {
            xWalkView.getNavigationHistory().navigate(
                    XWalkNavigationHistory.Direction.BACKWARD, 1);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("退出学习");
            dialog.setPositiveButton("退出", new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CrossWalkActivity.super.onBackPressed();
                }
            });
            dialog.setMessage("现在要退出学习吗？");
            dialog.create().show();
        }

    }

}
