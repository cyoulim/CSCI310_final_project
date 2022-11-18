package com.example.csci310_final_project;

public class loginValidator {
    boolean email_checker(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.length() > 0 && email.matches(emailPattern)) return true;
        return false;
    }
    boolean password_checker(String password){
        return password.length() >= 5;
    }
}
