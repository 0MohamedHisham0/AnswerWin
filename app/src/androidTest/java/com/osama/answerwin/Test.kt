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
        fun convertToAllString(s : String) {

            for (i in s){
                var b  = 0

                when(b){
                    0 -> if (i.equals(0..9) ) {s.set(b,"") }
                    1-> if (i.equals(0..9) ) {s.set(b,"") }
                    2 -> if (i.equals(0..9) ) {s.set(b,"") }
                    3 -> if (i.equals(0..9) ) {s.set(b,"") }
                }
                b++
            }
        }


    }
}

private operator fun String.set(b: Int, value: String) {

}
