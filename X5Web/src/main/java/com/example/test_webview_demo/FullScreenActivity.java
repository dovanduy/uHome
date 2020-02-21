package com.example.test_webview_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.example.test_webview_demo.utils.WebViewJavaScriptFunction;
import com.example.test_webview_demo.utils.X5WebView;

public class FullScreenActivity extends Activity {

	/**
	 * 用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式
	 */

	X5WebView webView;

	String app_name = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (app_name==null){
			Intent intent = getIntent();
			app_name = intent.getStringExtra("app_name");
		}

		setContentView(R.layout.filechooser_layout);
		webView = (X5WebView) findViewById(R.id.web_filechooser);
		Point point = new Point();
		getWindowManager().getDefaultDisplay().getSize(point);
		ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
		layoutParams.height = point.x;
		layoutParams.width = point.y*1280/752;
		webView.setLayoutParams(layoutParams);
		webView.loadUrl(app_name);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		webView.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
		webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

			@Override
			public void onJsFunctionCalled(String tag) {

			}

			@JavascriptInterface
			public void onX5ButtonClicked() {
				FullScreenActivity.this.enableX5FullscreenFunc();
			}

			@JavascriptInterface
			public void onCustomButtonClicked() {
				FullScreenActivity.this.disableX5FullscreenFunc();
			}

			@JavascriptInterface
			public void onLiteWndButtonClicked() {
				FullScreenActivity.this.enableLiteWndFunc();
			}

			@JavascriptInterface
			public void onPageVideoClicked() {
				FullScreenActivity.this.enablePageVideoFunc();
			}
		}, "Android");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		try {
			super.onConfigurationChanged(newConfig);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////////
	// 向webview发出信息
	private void enableX5FullscreenFunc() {

		if (webView.getX5WebViewExtension() != null) {
			Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void disableX5FullscreenFunc() {
		if (webView.getX5WebViewExtension() != null) {
			Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void enableLiteWndFunc() {
		if (webView.getX5WebViewExtension() != null) {
			Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void enablePageVideoFunc() {
		if (webView.getX5WebViewExtension() != null) {
			Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("退出学习");
		dialog.setPositiveButton("退出", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				FullScreenActivity.super.onBackPressed();
			}
		});
		dialog.setMessage("现在要退出学习吗？");
		dialog.create().show();


	}
}
