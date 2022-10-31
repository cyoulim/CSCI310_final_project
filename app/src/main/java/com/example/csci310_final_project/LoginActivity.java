package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        Button login_btn = (Button)findViewById(R.id.buttonLogin);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToProfile(); }
        });
        Button register_btn = (Button)findViewById(R.id.buttonRegister);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToRegister(); }
        });
    }

    private void switchToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void switchToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}