package cn.pompip.okhttplib;

public interface SuccessBack<T extends Entity> {
    void success(T t);
}
