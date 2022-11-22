package com.example.csci310_final_project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class unitTest10 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidDdl() {
        assertEquals("basic test 01", false, tester.isValidDdl(null));
        assertEquals("basic test 02", false, tester.isValidDdl(""));
        assertEquals("basic test 03", true, tester.isValidDdl("11/12"));
    }
}
