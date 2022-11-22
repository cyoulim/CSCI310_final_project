package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class unitTest06 {
    private registerValidator tester;
    @Before
    public void setup(){
        tester = new registerValidator();
    }
    @Test
    public void testIsValidEmail() {
        assertFalse(tester.email_checker(""));
        assertFalse(tester.email_checker("name"));
        assertFalse(tester.email_checker("@usc.edu"));
        assertTrue(tester.email_checker("name@email.com"));
    }
}
