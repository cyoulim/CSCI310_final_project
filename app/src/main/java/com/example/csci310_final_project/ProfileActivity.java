package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private String yourUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        yourUserId = getIntent().getStringExtra("yourUserId");
        init();
    }

    private void init(){
        Button profile_btn = (Button)findViewById(R.id.button01);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToProfile(yourUserId); }
        });
        Button post_btn = (Button)findViewById(R.id.button02);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToPost(yourUserId); }
        });
        Button accept_btn = (Button)findViewById(R.id.button03);
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToAccept(yourUserId); }
        });
        Button match_btn = (Button)findViewById(R.id.button04);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToMatch(yourUserId); }
        });
        Button logout_btn = (Button)findViewById(R.id.logout_Button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToLogin(); }
        });

        EditText username = (EditText) findViewById(R.id.username);
        EditText bio = (EditText) findViewById(R.id.bio);
        EditText image = (EditText) findViewById(R.id.image);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(yourUserId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("test", "DocumentSnapshot data: " + document.getData());
                        if (document.get("username") != null) {
                            username.setText(document.get("username").toString());
                        }
                        if (document.get("bio") != null) {
                            bio.setText(document.get("bio").toString());
                        }
                        if (document.get("image") != null) {
                            image.setText(document.get("image").toString());
                        }
                    } else {
                        Log.d("test", "No such document");
                    }
                } else {
                    Log.d("test", "get failed with ", task.getException());
                }
            }
        });

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("user");
        Button save_btn = (Button)findViewById(R.id.save);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("user")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                collectionReference.document(yourUserId).update("bio", bio.getText().toString());
                                collectionReference.document(yourUserId).update("image", image.getText().toString());
                                collectionReference.document(yourUserId).update("username", username.getText().toString());
                            } else {
                                Log.d("test", "Error getting documents: ", task.getException());
                            }
                        });
                }
            });
    }

    private void switchToProfile(String yourUserId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToPost(String yourUserId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToAccept(String yourUserId) {
        Intent intent = new Intent(this, Accepted.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToMatch(String yourUserId) {
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
