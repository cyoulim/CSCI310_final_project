package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }
    private void init(){
        Button profile_btn = (Button)findViewById(R.id.button01);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToProfile(); }
        });
        Button post_btn = (Button)findViewById(R.id.button02);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToPost(); }
        });
        Button accept_btn = (Button)findViewById(R.id.button03);
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToAccept(); }
        });
        Button match_btn = (Button)findViewById(R.id.button04);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToMatch(); }
        });
        Button logout_btn = (Button)findViewById(R.id.logout_Button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToLogin(); }
        });
    }

    private void switchToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void switchToPost() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void switchToAccept() {
        Intent intent = new Intent(this, Accepted.class);
        startActivity(intent);
    }

    private void switchToMatch() {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
