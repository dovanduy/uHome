package com.uhome.commonlib;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public abstract class BaseLauncherActivity extends AppCompatActivity {


        private SimpleAdapter gridAdapter;
        private GridView gridView;
        private ArrayList<HashMap<String, Object>> items;
        private Context context;

        private static String[] intentArgs;
        private static int[] iconResourse;
        private static String[] titles;
        private static boolean main_initialized = false;

        protected abstract String[] initArgs();

        protected abstract int[] initResource();

        protected abstract String[] initTitle();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.context = this;
            setContentView(R.layout.activity_main_advanced);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            reqPermission();
            intentArgs = initArgs();
            iconResourse = initResource();
            titles = initTitle();
            if (!main_initialized) {
                this.new_init();
            }
            initTips();

        }

        protected void initTips(){
            final TextView appVersion = findViewById(R.id.app_version);
            appVersion.setText("APP版本："+BuildConfig.VERSION_NAME);
//            BrowserInit.init(this, new BrowserInit.InitListener() {
//                @Override
//                public void callback(boolean isSuccess, String version) {
//                    if (isSuccess){
//                        LinearLayout loader_progress = findViewById(com.example.test_webview_demo.R.id.loader_progress);
//                        loader_progress.setVisibility(View.GONE);
//                        Toast.makeText(context, "内核加载完成", Toast.LENGTH_SHORT).show();
//                        appVersion.append("\n内核加载成功，版本："+ QbSdk.getTbsVersion(getApplicationContext()));
//                    }else {
//                        appVersion.append("\n内核加载失败");
//                    }
//                }
//            });
        }


        @Override
        protected void onResume() {
            this.new_init();
            super.onResume();
        }

        private void new_init() {
            items = new ArrayList<HashMap<String, Object>>();
            this.gridView = (GridView) this.findViewById(R.id.item_grid);

            if (gridView == null)
                throw new IllegalArgumentException("the gridView is null");


            HashMap<String, Object> item = null;
            for (int i = 0; i < titles.length; i++) {
                item = new HashMap<String, Object>();
                item.put("title", titles[i]);
                item.put("icon", iconResourse[i]);

                items.add(item);
            }
            this.gridAdapter = new SimpleAdapter(this, items,
                    R.layout.function_block, new String[]{"title", "icon"},
                    new int[]{R.id.Item_text, R.id.Item_bt});
            if (null != this.gridView) {
                this.gridView.setAdapter(gridAdapter);
                this.gridAdapter.notifyDataSetChanged();
                this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> gridView, View view,
                                            int position, long id) {
                        if (isTimeOut()) {
                            showQuiteDialog();
                            return;
                        }
                        ;
                        if (position < intentArgs.length) {
                            Intent intent = new Intent();
                            intent.setAction("cn.pompip.webview");
//                            intent.setData(Uri.parse("intentArgs[position]"));
                            intent.putExtra("app_name", intentArgs[position]);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "课程添加中", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            main_initialized = true;

        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    this.tbsSuiteExit();
            }
            return super.onKeyDown(keyCode, event);
        }

        private void tbsSuiteExit() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("退出系统？");
            dialog.setPositiveButton("退出", new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Process.killProcess(Process.myPid());
                }
            });
            dialog.setMessage("我们有更优秀的线下课程，如需体验请咨询老师");
            dialog.create().show();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 100) {
                for (int i : grantResults) {
                    if (i != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须要权限才能正常使用", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        }

        private boolean isTimeOut() {
            Calendar instance = Calendar.getInstance();
            instance.set(2020, 2, 20);
            return new Date().after(instance.getTime());
        }

        private void showQuiteDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle("使用到期了")
                    .setMessage("我们有更优秀的线下课程，如需体验请咨询\n如需继续使用请联系老师")
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Process.killProcess(Process.myPid());
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Process.killProcess(Process.myPid());
                        }
                    })
                    .create().show();
        }

        private void reqPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                List<String> permissions = new ArrayList<>(Arrays.asList(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE));
                Iterator<String> iterator = permissions.iterator();
                while (iterator.hasNext()) {
                    String p = iterator.next();
                    if (ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED) {
                        iterator.remove();
                    } else {
                        String[] pArray = permissions.toArray(new String[]{});
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                            ActivityCompat.requestPermissions(this, pArray, 100);
                        } else {
                            ActivityCompat.requestPermissions(this, pArray, 100);
                        }
                        break;
                    }
                }
            }
        }
}
