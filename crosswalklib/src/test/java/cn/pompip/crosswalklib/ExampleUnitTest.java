package cn.pompip.crosswalklib;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        while (linkedList.size()<100){
            int e = (int) (Math.random()*144);
            if (!linkedList.contains(e)){
                linkedList.add(e);
            }
        }
        Collections.sort(linkedList);
        System.out.println(linkedList);
        while (linkedList.size()>80){
            int e = (int) (Math.random()*(linkedList.size()));
            if (e>0&&e<linkedList.size()-1){
                linkedList.remove(e);
            }
        }
        System.out.println(linkedList);
        assertEquals(4, 2 + 2);
    }
}