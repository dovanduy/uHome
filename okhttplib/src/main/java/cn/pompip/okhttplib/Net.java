package cn.pompip.okhttplib;


import com.alibaba.fastjson.JSON;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Net {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    Request request;
    Type  type;
    Callback callback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

        }
    };
    public static Net get(){
        return new Net();
    }

    public Net url(String url) {
        request = new Request.Builder().get().url(url).build();
        return this;
    }

    public Net type(Object object){
        type = object.getClass();
        return this;
    }


    public void success(SuccessBack successBack) {
        Call call = okHttpClient.newCall(request);

    }
}

