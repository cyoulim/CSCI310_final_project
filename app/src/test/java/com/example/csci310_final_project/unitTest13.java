package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class unitTest13 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidName() {
        assertEquals("basic test 01", false, tester.isValidRent(null));
        assertEquals("basic test 02", false, tester.isValidRent("test"));
        assertEquals("basic test 03", true, tester.isValidRent("123"));
    }
}
