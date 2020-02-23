package com.uhome.taihe.app3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uhome.commonlib.BaseLauncherActivity;

public class MainActivity extends BaseLauncherActivity {


    private static final String[] intentArgs = {
            "file:///android_asset/app_sx_tb/index.html",
            "file:///android_asset/app_wl_tb/index.html",
            "file:///android_asset/app_hx_tb/index.html",
            "https://yy2tbstu.uhongedu.com"};
    private static final int[] iconResourse = {
            R.mipmap.icon_sx_tb,
            R.mipmap.icon_wl_tb,
            R.mipmap.icon_hx_tb,
            R.mipmap.icon_yy_tb,
            R.drawable.filechooser};
    private static final String[] titles = {
            "初中数学同步","初中物理同步","初中化学同步","初中英语同步","开发中"
    };

    @Override
    protected String[] initArgs() {
        return intentArgs;
    }

    @Override
    protected int[] initResource() {
        return iconResourse;
    }

    @Override
    protected String[] initTitle() {
        return titles;
    }
}
