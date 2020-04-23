package cn.pompip.okhttplib;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

//        assertEquals("cn.pompip.okhttplib.test", appContext.getPackageName());
        assertEquals(4, 2 + 2);
        Net.url("http://api.fir.im/apps/latest/5e4fbc22f94548462de1b0e4?api_token=1f2ccea22cb6ea39ef7795624ca57fd1")
                .get()
                .success(new NetCallback<UpdateEntity>() {
                    @Override
                    public void success(UpdateEntity entity) {
                        System.out.println(entity);
                    }
                })
                .fail(new FailCallback() {
                    @Override
                    public void fail(int code) {
                        System.out.println(code);
                    }
                });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
