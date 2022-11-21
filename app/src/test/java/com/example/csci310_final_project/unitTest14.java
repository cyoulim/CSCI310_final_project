package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class unitTest14 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidRent() {
        assertEquals("basic test 01", false, tester.isValidUt(null));
        assertEquals("basic test 02", false, tester.isValidUt("test"));
        assertEquals("basic test 03", true, tester.isValidUt("123"));
    }
}