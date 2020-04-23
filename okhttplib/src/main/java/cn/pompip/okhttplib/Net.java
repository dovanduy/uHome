package cn.pompip.okhttplib;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Net {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    private String method;
    private String url;
    private Map<String, String> params;
    private Request.Builder builder;



    private Net() {
    }

    public Net get() {
        method = "get";
        return this;
    }

    public Net post() {
        method = "post";
        return this;
    }

    public Net params(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public Net param(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public static Net url(String url) {
        Net net = new Net();

        Request.Builder builder = new Request.Builder();
        Map<String, String> params = new HashMap<>();
        net.builder = builder;
        net.url = url;
        net.params = params;
        return net;
    }





    public  void execute(final NetCallback callback) {
        buildRequest();
        Call call = okHttpClient.newCall(builder.build());
        final NetResult netResult = new NetResult(this);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                netResult.setResponseBody(body);
                if (callback != null) {
                    callback.result(netResult);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            netResult.setError(e);

        }
    }


    private void buildRequest() {
        if (url != null) {
            builder.url(url);
        } else {
            throw new RuntimeException("no url");
        }
        if (method == null) {
            builder.get();
        } else if (method.equals("get")) {
            builder.get();
        } else if (method.equals("post")) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                bodyBuilder.add(entry.getKey(), entry.getValue());
            }
            RequestBody requestBody = bodyBuilder.build();
            builder.post(requestBody);
        } else {
            throw new RuntimeException("unSupoort");
        }
    }
}

