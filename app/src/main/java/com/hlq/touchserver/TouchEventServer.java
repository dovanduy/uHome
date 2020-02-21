package com.hlq.touchserver;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;
import android.util.MonthDisplayHelper;

import com.hlq.touchserver.wrappers.ServiceManager;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TouchEventServer {
    private static final String HOST = "singleTouch";
    private static int MODE = 0;
    private static final String TAG = "TouchEventServer";
    private static final byte TYPE_KEYCODE = 1;
    private static final byte TYPE_MOTION = 0;
    private final byte[] contentBytes = new byte[18];
    private final byte[] lenBytes = new byte[4];
    private EventInjector mEventInjector;
    private  InputStream mInputStream;
    private  ServiceManager mServiceManager;

    public static void main(String[] strArr) {
        int i = 0;
        int i2 = 1000000;
        if (strArr.length > 0) {
            if ("screenshot".equals(strArr[0])) {
                MODE = 1;
                System.out.println("enable screenshot");
            } else if ("h264".equals(strArr[0])) {
                MODE = 2;
                System.out.println("enable h264 ");
                if (strArr.length > 1) {
                    i = Integer.parseInt(strArr[1]);
                }
                if (strArr.length > 2) {
                    try {
                        i2 = Integer.parseInt(strArr[2]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        i2 = 800000;
                    }
                }
            }
        }
        try {
            new TouchEventServer(i, i2).loop();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private  TouchEventServer(int r6,int r7) throws IOException{


            LocalServerSocket r1 = new LocalServerSocket(HOST);
            LocalSocket accept = r1.accept();
            LogUtil.d("client bind SUCCESS !");
            mServiceManager = new ServiceManager();
            if (MODE == 1){

                OutputStream outputStream = accept.getOutputStream();
                Screenshot mScreenshot = new Screenshot(mServiceManager,outputStream);
                Thread mThread = new Thread(mScreenshot,"Screenshot");
                mThread.start();
            }else if (MODE ==2){
                FileDescriptor fileDescriptor = accept.getFileDescriptor();
                ScreenEncoder screenEncoder = new ScreenEncoder(r6,r7,fileDescriptor);
                Thread mThread = new Thread(screenEncoder,"H264");
                mThread.start();
            }else {
                mInputStream = accept.getInputStream();
                r1.close();
            }


    }
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0080 A[SYNTHETIC, Splitter:B:27:0x0080] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
//    private TouchEventServer(int r6, int r7) throws IOException {
        /*
            r5 = this;
            r5.<init>()
            r0 = 4
            byte[] r0 = new byte[r0]
            r5.lenBytes = r0
            r0 = 18
            byte[] r0 = new byte[r0]
            r5.contentBytes = r0
            r0 = 0
            android.net.LocalServerSocket r1 = new android.net.LocalServerSocket     // Catch:{ IOException -> 0x0075 }
            java.lang.String r2 = "singleTouch"
            r1.<init>(r2)     // Catch:{ IOException -> 0x0075 }
            java.lang.String r0 = "OK !"
            com.hlq.touchserver.LogUtil.d(r0)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            android.net.LocalSocket r0 = r1.accept()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            java.lang.String r2 = "client bind SUCCESS !"
            com.hlq.touchserver.LogUtil.d(r2)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            com.hlq.touchserver.wrappers.ServiceManager r2 = new com.hlq.touchserver.wrappers.ServiceManager     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r2.<init>()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r5.mServiceManager = r2     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            int r2 = MODE     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r3 = 1
            if (r2 != r3) goto L_0x0046
            java.lang.Thread r6 = new java.lang.Thread     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            com.hlq.touchserver.Screenshot r7 = new com.hlq.touchserver.Screenshot     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            com.hlq.touchserver.wrappers.ServiceManager r2 = r5.mServiceManager     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            java.io.OutputStream r3 = r0.getOutputStream()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r7.<init>(r2, r3)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            java.lang.String r2 = "Screenshot"
            r6.<init>(r7, r2)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r6.start()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            goto L_0x005e
        L_0x0046:
            int r2 = MODE     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r3 = 2
            if (r2 != r3) goto L_0x005e
            java.lang.Thread r2 = new java.lang.Thread     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            com.hlq.touchserver.ScreenEncoder r3 = new com.hlq.touchserver.ScreenEncoder     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            java.io.FileDescriptor r4 = r0.getFileDescriptor()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r3.<init>(r6, r7, r4)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            java.lang.String r6 = "H264"
            r2.<init>(r3, r6)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r2.start()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
        L_0x005e:
            java.io.InputStream r6 = r0.getInputStream()     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r5.mInputStream = r6     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r1.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r6 = move-exception
            r6.printStackTrace()
        L_0x006c:
            return
        L_0x006d:
            r6 = move-exception
            goto L_0x007e
        L_0x006f:
            r6 = move-exception
            r0 = r1
            goto L_0x0076
        L_0x0072:
            r6 = move-exception
            r1 = r0
            goto L_0x007e
        L_0x0075:
            r6 = move-exception
        L_0x0076:
            java.lang.String r7 = "TouchEventServer"
            java.lang.String r1 = "TouchEventServer: "
            android.util.Log.e(r7, r1, r6)     // Catch:{ all -> 0x0072 }
            throw r6     // Catch:{ all -> 0x0072 }
        L_0x007e:
            if (r1 == 0) goto L_0x0088
            r1.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0088:
            throw r6
        */
//        throw new UnsupportedOperationException("Method not decompiled: com.hlq.touchserver.TouchEventServer.<init>(int, int):void");
//    }

    private ByteBuffer readByte(byte[] bArr, int i) {
        if (i > bArr.length) {
            bArr = new byte[i];
        }
        int i2 = 0;
        while (i2 < bArr.length && i2 != i) {
            try {
                int read = this.mInputStream.read(bArr, i2, i - i2);
                if (read <= -1) {
                    break;
                }
                i2 += read;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i2 == i) {
            return ByteBuffer.wrap(bArr, 0, i2);
        }
        return null;
    }

    private void loop() {
        this.mEventInjector = new EventInjector(this.mServiceManager.getInputManager(), this.mServiceManager.getPowerManager());
        this.mEventInjector.checkScreenOn();
        while (true) {
            ByteBuffer readByte = readByte(this.lenBytes, this.lenBytes.length);
            if (readByte != null) {
                int i = readByte.getInt();
                if (i > 0) {
                    ByteBuffer readByte2 = readByte(this.contentBytes, i);
                    if (readByte2 != null) {
                        try {
                            handleEvent(readByte2);
                        } catch (Exception e) {
                            LogUtil.d(Log.getStackTraceString(e));
                        }
                    }
                }
            }
        }
    }

    private void handleEvent(ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        if (b == 0) {
            byte b2 = byteBuffer.get();
            if (b2 != 8) {
                switch (b2) {
                    case 0:
                    case 2:
                        this.mEventInjector.injectInputEvent(b2, byteBuffer.getInt(), byteBuffer.getInt());
                        return;
                    case 1:
                        this.mEventInjector.injectInputEvent(b2, -1, -1);
                        return;
                    default:
                        return;
                }
            } else {
                this.mEventInjector.injectScroll(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getFloat(), byteBuffer.getFloat());
            }
        } else if (b == 1) {
            int i = byteBuffer.getInt();
            if (i <= 0) {
                this.mEventInjector.checkScreenOff();
            } else {
                this.mEventInjector.injectKeycode(i);
            }
        }
    }
}
