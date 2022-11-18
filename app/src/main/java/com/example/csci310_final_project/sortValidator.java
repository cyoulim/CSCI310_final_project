package com.example.csci310_final_project;

import java.util.Collections;
import java.util.List;

public class sortValidator {
    List<Accepted.UserInfo> sortByRent(List<Accepted.UserInfo> arr){
        Collections.sort(arr, (a, b)->(Integer.valueOf(b.rent) - Integer.valueOf(a.rent)));
        return arr;
    }
    List<Accepted.UserInfo> sortByUtilities(List<Accepted.UserInfo> arr){
        Collections.sort(arr, (a,b)->(Integer.valueOf(b.utilities) - Integer.valueOf(a.utilities)));
        return arr;
    }
}
