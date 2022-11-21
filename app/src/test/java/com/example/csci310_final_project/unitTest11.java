package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class unitTest11 {
    private postValidator tester;
    @Before
    public void setup(){
        tester = new postValidator();
    }
    @Test
    public void testsIsValidBio() {
        assertEquals("basic test 01", false, tester.isValidBio(null));
        assertEquals("basic test 02", true, tester.isValidBio("bio"));
        assertEquals("basic test 03", false, tester.isValidBio(""));
    }
}