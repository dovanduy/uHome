package com.example.myapplication

import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.util.DisplayMetrics
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val TAG = "ExampleInstrumentedTest"
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("", appContext.packageName)
    }

    @Test
    fun testMediaFormat() {
        val url =
            "http://video.like.video/asia_live/7h5/M0E/43/BD/rPsbAF5DyOuEf29nAAAAAO-EF8k621.mp4?crc=4018411465&type=5&i=05f14aa2c000000a4a7394e290&o264=64"

        val mr = MediaMetadataRetriever()
        mr.setDataSource(
            url,
            HashMap()
        )
        val image_count = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)
        Log.e(TAG, "image_count:" + image_count)
        val duration = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        Log.e(TAG,"duration:"+duration);
//        Log.e(TAG,"frame_rate:"+image_count.toLong()/duration.toLong())
        val bitrate = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)
        Log.e(TAG, "bitrate" + bitrate)

        mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)

        mr.getFrameAtTime()


        mr.release()

        val ex = MediaExtractor()

        ex.setDataSource(url,HashMap())
        Log.e(TAG,"trackCount:"+ex.trackCount)


        for (i in 0 until ex.trackCount){
            var fm = ex.getTrackFormat(i)

            var bps = fm.getInteger(MediaFormat.KEY_BIT_RATE,0)
            Log.e(TAG,"getTrackFormat:"+" count:"+i+" bps:"+bps)
            var fps = fm.getInteger(MediaFormat.KEY_FRAME_RATE,0)
            Log.e(TAG,"getTrackFormat"+" count:"+i+" fps:"+fps)
            val width = fm.getInteger(MediaFormat.KEY_WIDTH,0);
            Log.e(TAG,"width:"+width)
            val height = fm.getInteger(MediaFormat.KEY_HEIGHT,0)
            Log.e(TAG,"height:"+height)

        }
        ex.release()


    }


}
