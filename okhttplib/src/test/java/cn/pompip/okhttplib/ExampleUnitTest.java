package cn.pompip.okhttplib;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        Net.url("http://api.bq04.com/apps/latest/5e4fbc22f94548462de1b0e4?api_token=1f2ccea22cb6ea39ef7795624ca57fd1")
                .get()
                .execute(new NetCallback() {
                    @Override
                    public void result(NetResult result) {

                        UpdateEntity json = result.json(UpdateEntity.class);
                        System.out.println(json);
                    }
                });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNum() throws InterruptedException {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
            }
        },100,100, TimeUnit.MILLISECONDS);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}