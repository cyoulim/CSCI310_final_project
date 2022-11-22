package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class unitTest04 {
    private registerValidator tester;
    @Before
    public void setup(){
        tester = new registerValidator();
    }
    @Test
    public void testIsValidPassword() {
        assertFalse(tester.password_checker(""));
        assertFalse(tester.password_checker("123"));
        assertTrue(tester.password_checker("12345"));
        assertTrue(tester.password_checker("12345678"));
    }
}
