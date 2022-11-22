package com.example.csci310_final_project;

import android.util.*;

public class postValidator {
    boolean isValidName(String username){
        return username != null && !username.isEmpty();
    }
    boolean isValidBio(String bio){
        return bio != null && !bio.isEmpty();
    }
    boolean isValidDdl(String ddl){
        return ddl != null && !ddl.isEmpty();
    }
    boolean isValidAddress(String address){
        return address != null && !address.isEmpty();
    }
    boolean isValidRent(String rent){
        if(rent == null) return false;
        try{
            int val = Integer.parseInt(rent);
            return true;
        }catch(Exception e){
            Log.d("test", "invalid rent");
            return false;
        }
    }
    boolean isValidUt(String ut){
        if(ut == null) return false;
        try{
            int val = Integer.parseInt(ut);
            return true;
        }catch(Exception e){
            Log.d("test", "invalid rent");
            return false;
        }
    }
    boolean isValidRangeUt(String ut){
        int ut_ = Integer.valueOf(ut);
        if(ut_ <= 0){
            return false;
        }
        return true;
    }

    boolean isValidRange_rent(String rent){
        int rent_ = Integer.valueOf(rent);
        if(rent_ <= 0){
            return false;
        }
        return true;
    }
}
