package com.bonree.touchserver;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VirtualDisplay {
    private static final String TAG = "VirtualDisplay";
    private IBinder mDisplayToken;
    private Surface mSurface;
    private Class<?> mSurfaceControlCls;

    public VirtualDisplay(Class<?> cls, IBinder iBinder, Surface surface) {
        this.mSurfaceControlCls = cls;
        this.mDisplayToken = iBinder;
        this.mSurface = surface;
    }

    public static VirtualDisplay createVirtualDisplay(String str, Surface surface, Rect rect, Rect rect2) {
        Method method = null;
        Surface surface2 = surface;
        try {
            Class cls = Class.forName("android.view.SurfaceControl");
            Method method2 = cls.getMethod("createDisplay", new Class[]{String.class, Boolean.TYPE});
            Method method3 = cls.getMethod("setDisplaySurface", new Class[]{IBinder.class, Surface.class});
            Method method4 = cls.getMethod("setDisplayProjection", new Class[]{IBinder.class, Integer.TYPE, Rect.class, Rect.class});
            Method method5 = cls.getMethod("setDisplayLayerStack", new Class[]{IBinder.class, Integer.TYPE});
            Method method6 = cls.getMethod("openTransaction", new Class[0]);
            method = cls.getMethod("closeTransaction", new Class[0]);
            IBinder iBinder = (IBinder) method2.invoke(null, new Object[]{str, Boolean.valueOf(true)});
            method6.invoke(null, new Object[0]);
            method3.invoke(null, new Object[]{iBinder, surface2});
            method4.invoke(null, new Object[]{iBinder, Integer.valueOf(0), rect, rect2});
            method5.invoke(null, new Object[]{iBinder, Integer.valueOf(0)});
            method.invoke(null, new Object[0]);
            return new VirtualDisplay(cls, iBinder, surface2);
        } catch (Exception e) {
            Log.e(TAG, "createVirtualDisplay: ", e);
            return null;
        } catch (Throwable th) {
            try {
                method.invoke(null, new Object[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            throw th;
        }
    }

    @TargetApi(14)
    public void destroyDisplay() {
        if (this.mSurfaceControlCls != null && this.mDisplayToken != null) {
            try {
                this.mSurfaceControlCls.getMethod("destroyDisplay", new Class[]{IBinder.class}).invoke(null, new Object[]{this.mDisplayToken});
                this.mSurfaceControlCls = null;
                this.mDisplayToken = null;
                this.mSurface.release();
                this.mSurface = null;
                Log.w(TAG, "destroyDisplay");
            } catch (Exception e) {
                Log.e(TAG, "destroyDisplay: ", e);
            }
        }
    }
}
