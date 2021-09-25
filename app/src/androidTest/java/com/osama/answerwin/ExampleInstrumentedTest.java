package com.osama.answerwin;

import android.text.format.DateFormat;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void Test() {
        long timeInt = Long.parseLong("1632336906");
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeInt * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        System.out.println("---------------------------------");
        System.out.println(date);

    }

    @Test
    public void Test2() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        long ts = dateFormat.parse("22-10-2021").getTime() / 1000;
        System.out.println(ts);
    }


}