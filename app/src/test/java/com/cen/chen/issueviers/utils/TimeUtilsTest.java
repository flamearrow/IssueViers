package com.cen.chen.issueviers.utils;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;

/**
 * Created by flamearrow on 1/7/16.
 */
public class TimeUtilsTest {
    @Test
    public void testPareFromString() {
        String input = "2012-04-02T09:18:15Z";
        Calendar parsed = TimeUtils.parseFromString(input);
        assertEquals(2012, parsed.get(Calendar.YEAR));
        assertEquals(3, parsed.get(Calendar.MONTH));
        assertEquals(2, parsed.get(Calendar.DAY_OF_MONTH));
        assertEquals(9, parsed.get(Calendar.HOUR));
        assertEquals(18, parsed.get(Calendar.MINUTE));
        assertEquals(15, parsed.get(Calendar.SECOND));
    }
}
