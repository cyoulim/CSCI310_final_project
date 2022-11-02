package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.email);
                EditText password = (EditText) findViewById(R.id.password);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("user")
                        .whereEqualTo("email", email.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() <= 0) {
                                Log.d("test", "no such email");
                                loginDenied();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d("test", document.getId() + " => " + document.getData());
                                String pass = document.getString("password");
                                Log.d("pass", pass);
                                Log.d("password", password.getText().toString());
                                if (!pass.equals(password.getText().toString())) {
                                    Log.d("test", "incorrect password");
                                    loginDenied();
                                } else {
                                    switchToProfile(document.getId());
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });
        Button register_btn = (Button)findViewById(R.id.buttonRegister);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToRegister(); }
        });
    }

    private void loginDenied() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        email.setText("");
        password.setText("");
        TextView login_error = new TextView (this);
        login_error.setText("incorrect email or password");
        linearLayout.addView(login_error);
    }

    private void switchToProfile(String yourUserId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}