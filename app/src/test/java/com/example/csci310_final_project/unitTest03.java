package com.example.csci310_final_project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class unitTest03 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidRange_rent() {
        assertEquals("basic test 01", true, tester.isValidRange_rent("1000"));
        assertEquals("basic test 02", false, tester.isValidRange_rent("-100"));
    }
}
