package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class unitTest12 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidAddress() {
        assertEquals("basic test 01", false, tester.isValidAddress(null));
        assertEquals("basic test 02", true, tester.isValidAddress("address"));
        assertEquals("basic test 03", false, tester.isValidAddress(""));
    }
}