package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userName = "Winnie"; //TODO: change to userId of current user
        db.collection("invitation")
                .whereEqualTo("username", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList<Long> acceptances = (ArrayList<Long>) document.get("accept");
                                addMatchButtons(document.getId(), acceptances, db, document);
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void addMatchButtons(String invitationId, ArrayList<Long> acceptances, FirebaseFirestore db, QueryDocumentSnapshot document) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linearlayout02);
        if (acceptances.size() > 0) {
            TextView invitation = new TextView(this);
            invitation.setText("Invitation " + invitationId + " responses");
            linearLayout.addView(invitation);

            for (Long userId : acceptances) {
                TextView user = new TextView(this);
                user.setText("user " + userId);

                Button button = new Button(this);
                ArrayList<Long> matched = (ArrayList<Long>) document.get("matched");
                if (matched != null && matched.contains(userId)) {
                    button.setText("unmatch");
                } else {
                    button.setText("match!");
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (button.getText().toString() == "unmatch") {
                            button.setText("match!");
                            // delete user from matched under invitation
                            DocumentReference ref = db.collection("invitation").document(invitationId);
                            ref.update("matched", FieldValue.arrayRemove(userId));
                        } else if (button.getText().toString() == "match!"){
                            button.setText("unmatch");
                            // add user to matched under invitation
                            DocumentReference ref = db.collection("invitation").document(invitationId);
                            ref.update("matched", FieldValue.arrayUnion(userId));
                        }
                    }
                });
                linearLayout.addView(user);
                linearLayout.addView(button);
            }
        }
    }

    private void switchToProfile() {
        // TODO: create a profile page
        // Intent intent = new Intent(this, ProfileActivity.class);
        // startActivity(intent);
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
        // TODO: create a login/logout page
        // Intent intent = new Intent(this, LoginActivity.class);
        // startActivity(intent);
    }
}
