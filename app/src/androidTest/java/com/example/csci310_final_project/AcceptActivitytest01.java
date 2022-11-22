package com.example.csci310_final_project;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class AcceptActivitytest01 {
    private sortValidator tester;
    @Before
    public void setup(){
        tester = new sortValidator();
    }
    @Test
//    UserInfo(String id, String username, String bio, String deadline,
//             String address, String utilities, String rent)
    public void testsSortedByRent() {
        Accepted.UserInfo user1 = new Accepted.UserInfo("1", "Judy", "cs", "today", "address1", "1000", "100");
        Accepted.UserInfo user2 = new Accepted.UserInfo("2", "Winnie", "cs", "today", "address2", "3000", "50");
        Accepted.UserInfo user3 = new Accepted.UserInfo("3", "Yitian", "cs", "today", "address3", "1500", "150");
        List<Accepted.UserInfo> input = new ArrayList<>();
        input.add(user1);
        input.add(user2);
        input.add(user3);
        List<Accepted.UserInfo> expected = new ArrayList<>();
        expected.add(user3);
        expected.add(user1);
        expected.add(user2);
        assertEquals("basic test" + input, expected, tester.sortByRent(input));
    }
}