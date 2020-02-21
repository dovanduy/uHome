package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val instance = Calendar.getInstance()
        instance.set(2020, 1, 21)
        println(instance.time)
        println(Date())
        val after = Date().before(instance.time)
        println(after)
    }
}
