package com.osama.answerwin

import org.junit.Test
import java.util.*

class UnitTest {
    @Test
    fun getRandomWinners() {
        val rand = Random()

        var list: MutableList<String> = ArrayList()
        list.add("one")
        list.add("two")
        list.add("there")
        list.add("four")

        val numberOfElements = 4
        for (i in 0 until numberOfElements) {
            val randomIndex = rand.nextInt(list.size)
            val randomElement = list[randomIndex]
            list.removeAt(randomIndex)
            println(randomElement )
        }
    }

}