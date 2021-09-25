package com.osama.answerwin

import android.text.format.DateFormat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.collect.Lists
import com.osama.answerwin.Models.BooledModel
import org.junit.Test
import org.junit.runner.RunWith
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Test {
    @RunWith(AndroidJUnit4::class)
     class ExampleInstrumentedTest {
        @Test
        fun getRandomWinners() {
            val rand = Random()

            var list: MutableList<String> = ArrayList()
            list.add("one")
            list.add("two")
            list.add("there")
            list.add("four")

            val numberOfElements = 2
            for (i in 0 until numberOfElements) {
                val randomIndex = rand.nextInt(list.size)
                val randomElement = list[randomIndex]
                list.removeAt(randomIndex)
                println(randomElement +"HI")
            }
        }




    }
}