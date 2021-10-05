package com.osama.answerwin;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void getRandomWinners() {
        Random rand = new Random();
        List<String> givenList = Lists.newArrayList("one", "two", "three", "four");

        int numberOfElements = 2;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String randomElement = givenList.get(randomIndex);
            givenList.remove(randomIndex);

            System.out.println(randomElement);
        }
    }

    @Test
    public void convertToAllString(String s) {
        String newString = "";
        String newString2 = "";

        for (int i = 0; i < 4; i++) {
            newString = s.charAt(i) + newString;
        }

        newString2 = newString.replaceAll("\\d", "") + s.substring(4, s.length());
        System.out.println(newString2);
    }


}