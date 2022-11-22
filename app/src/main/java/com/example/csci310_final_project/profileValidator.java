package com.example.csci310_final_project;

import android.util.Log;

public class profileValidator {
    boolean isValidName(String username) {
        return username != null && !username.isEmpty();
    }

    boolean isValidBio(String bio) {
        return bio != null && !bio.isEmpty();
    }

    boolean isValidImg(String image) {
        return image != null && !image.isEmpty();
    }
}
