package cn.pompip.okhttplib;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class NetResult{
    private ResponseBody responseBody;
    public Net net;
    public boolean isSuccess;
    private String text;
    private Exception e;

    public NetResult(Net net) {
        this.net = net;
    }

    public void setResponseBody(ResponseBody responseBody) throws IOException {
        this.responseBody = responseBody;
        isSuccess = true;
        text = responseBody.string();
    }


    public <T extends Entity>T json(Class<T> tClass){
//        Type[] genericParameterTypes = getClass().getGenericInterfaces();
//        ParameterizedType pt = (ParameterizedType) genericParameterTypes[0];
//        Type[] actualTypeArguments = pt.getActualTypeArguments();
//        Type type = actualTypeArguments[0];
        T parse = JSON.parseObject(text, tClass);
        return parse;
    }

    @Override
    public String toString() {
        if (isSuccess){
            return text;
        }else {
            return e.toString();
        }
    }

    public void setError(IOException e) {
        isSuccess = false;
        this.e = e;
    }
}
