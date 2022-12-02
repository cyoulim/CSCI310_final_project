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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchActivity extends AppCompatActivity {
    private String yourUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("yourUserId", yourUserId);

        db.collection("invitation")
                .whereEqualTo("userId", yourUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ArrayList<String> acceptances = (ArrayList<String>) document.get("accept");
                                addMatchButtons(document.getId(), acceptances, db, document);
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("invitation")
                .whereArrayContains("matched", yourUserId)
                // .whereNotEqualTo("userId", yourUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                addInvitationsMatched(db, document);
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void addMatchButtons(String invitationId, ArrayList<String> acceptances, FirebaseFirestore db, QueryDocumentSnapshot document) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linearlayout02);
        if (acceptances.size() > 0) {
            TextView invitation = new TextView(this);
            invitation.setText("Invitation " + invitationId + " responses");
            linearLayout.addView(invitation);

            for (String userId : acceptances) {
                TextView user = new TextView(this);
                if (document.get("username") == null) {
                    user.setText("user: " + yourUserId);
                } else {
                    user.setText("user: " + document.get("username"));
                }
                TextView bio = new TextView(this);
                bio.setText("bio: " + document.get("bio").toString());

                Button button = new Button(this);
                ArrayList<String> matched = (ArrayList<String>) document.get("matched");
                int roommates = Integer.parseInt(document.get("roommates").toString());
                if (matched != null && matched.contains(userId)) {
                    button.setText("unmatch");
                } else if (matched != null && matched.size() >= roommates) {
                        button.setText("invitation full, cannot match!");
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
                        } else if (button.getText().toString() == "invitation full, cannot match!") {

                        }
                    }
                });
                linearLayout.addView(user);
                linearLayout.addView(bio);
                linearLayout.addView(button);
            }
        }
    }

    private void addInvitationsMatched(FirebaseFirestore db, QueryDocumentSnapshot document) {
        if (document.get("userId").toString().equals(yourUserId)) {
            Log.d("my own posts ", document.getId());
            return;
        }
        TextView invitationId = new TextView(this);
        invitationId.setText("You are matched with invitation " + document.getId());
        TextView inviter = new TextView(this);

        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.getId().equals(document.get("userId"))) {
                                    inviter.setText("inviter: " + doc.get("email"));
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });


        TextView address = new TextView(this);
        address.setText("address: " + document.get("address"));
        TextView rent = new TextView(this);
        rent.setText("rent: " + document.get("rent"));
        TextView utilities = new TextView(this);
        utilities.setText("utilities: " + document.get("utilities"));
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linearlayout02);
        linearLayout.addView(invitationId);
        linearLayout.addView(inviter);
        linearLayout.addView(address);
        linearLayout.addView(rent);
        linearLayout.addView(utilities);
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
