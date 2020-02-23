package com.uHome.taihe.app4;



import com.uhome.commonlib.BaseLauncherActivity;

public class MainActivity extends BaseLauncherActivity {
    private static final String[] intentArgs = {
            "file:///android_asset/app_xx_sx_sw/index.html",
            "file:///android_asset/app_xx_sx_tb/index.html",
            "file:///android_asset/app_ks_dx/index.html",
            "file:///android_asset/app_xy_js_dc/index.html",
            "https://speakavue.uhongedu.com"};
    private static final int[] iconResourse = {
            R.mipmap.icon_xs_sx_sw,
            R.mipmap.icon_xx_sx_tb,
            R.mipmap.icon_ks_dx,
            R.mipmap.icon_xy_js_dc,
            R.mipmap.icon_speaka,
            R.drawable.filechooser};
    private static final String[] titles = {
            "小学数学思维","小学数学同步","快速读写","小优极速单词","Speaka英语","开发中"
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
