package com.uhome.taihe.app2;


public class MainActivity extends com.example.test_webview_demo.MainActivity {

    private static final String[] intentArgs = {
            "file:///android_asset/app_sx_zk_jx/index.html/index.html",
            "file:///android_asset/app_wl_zk_jx/index.html",
            "file:///android_asset/app_hx_zk_jx/index.html",
            "file:///android_asset/app_sx_zk_gl/index.html"};
    private static final int[] iconResourse = {R.mipmap.icon_ss_zk_zt_jx,
            R.mipmap.icon_wl_zk_zt_jx,R.mipmap.icon_hx_zk_zt_jx,R.mipmap.icon_sx_zk_gl,
            R.drawable.filechooser};

    private static final String[] titles = {"数学中考集训","物理中考集训","化学中考集训","数学中考攻略","开发中"};


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
