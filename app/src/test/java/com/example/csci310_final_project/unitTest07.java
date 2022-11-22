package com.example.csci310_final_project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class unitTest07 {
    private profileValidator tester;
    @Before
    public void setup(){
        tester = new profileValidator();
    }
    @Test
    public void testsIsValidImg() {
        assertEquals("basic test 01", false, tester.isValidImg(null));
        assertEquals("basic test 02", false, tester.isValidImg(""));
        assertEquals("basic test 03", true, tester.isValidImg("img_url"));
    }
}
