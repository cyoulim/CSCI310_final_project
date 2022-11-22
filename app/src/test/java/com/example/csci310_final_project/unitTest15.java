package com.example.csci310_final_project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class unitTest15 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidRange_ut() {
        assertEquals("basic test 01", true, tester.isValidRangeUt("1000"));
        assertEquals("basic test 02", false, tester.isValidRangeUt("0"));
    }
}
