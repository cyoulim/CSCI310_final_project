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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public boolean emailAlreadyExists = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    private void init() {
        Button login_btn = (Button)findViewById(R.id.buttonLogin);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToLogin(); }
        });

        Button signup_btn = (Button)findViewById(R.id.buttonSignup);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText) findViewById(R.id.email);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("user")
                        .whereEqualTo("email", email.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d("size", task.getResult().size() + " ");
                                    if (task.getResult().size() >= 1) {
                                        emailAlreadyExists = true;
                                        registerDenied();
                                    } else {
                                        EditText password = (EditText) findViewById(R.id.password);
                                        Map<String, String> data = new HashMap<>();
                                        data.put("email", email.getText().toString());
                                        data.put("password", password.getText().toString());

                                        registerValidator validator = new registerValidator();
                                        if(!validator.email_checker(email.getText().toString())){
                                            Log.d("test", "invalid email");
                                            registerDeniedEmailFormat();
                                            return;
                                        }
                                        if(!validator.password_checker(password.getText().toString())){
                                            Log.d("test", "invalid password");
                                            registerDeniedPasswordFormat();
                                            return;
                                        }

                                        db.collection("user")
                                                .add(data)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("id", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                        switchToProfile(documentReference.getId());
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("error", "Error adding document", e);
                                                    }
                                                });

                                    }
                                } else {
                                    Log.d("test", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                }
        });

    }

    private void registerDenied() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_register);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        email.setText("");
        password.setText("");
        TextView register_error = (TextView) findViewById(R.id.errorMsg);
        register_error.setText("existing profile with email");
    }

    private void registerDeniedEmailFormat() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_register);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        email.setText("");
        password.setText("");
        TextView register_error = (TextView) findViewById(R.id.errorMsg);
        register_error.setText("email format incorrect");
    }

    private void registerDeniedPasswordFormat() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_register);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        email.setText("");
        password.setText("");
        TextView register_error = (TextView) findViewById(R.id.errorMsg);
        register_error.setText("password length less than 5 characters");

    }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void switchToProfile(String yourUserId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }
}
