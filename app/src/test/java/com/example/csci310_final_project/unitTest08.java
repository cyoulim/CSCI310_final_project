package com.example.csci310_final_project;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class unitTest08 {
    private profileValidator tester;
    @Before
    public void setup(){
        tester = new profileValidator();
    }
    @Test
    public void testsIsValidBio() {
        assertEquals("basic test 01", false, tester.isValidBio(null));
        assertEquals("basic test 02", false, tester.isValidBio(""));
        assertEquals("basic test 03", true, tester.isValidBio("Bio"));
    }
}
