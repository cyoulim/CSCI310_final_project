package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class unitTest05 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidName() {
        assertEquals("basic test 01", false, tester.isValidName(null));
        assertEquals("basic test 02", true, tester.isValidName("test"));
        assertEquals("basic test 03", false, tester.isValidName(""));
    }
}
