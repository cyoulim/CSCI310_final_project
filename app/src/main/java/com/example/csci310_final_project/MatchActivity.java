package com.example.csci310_final_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MatchActivity extends AppCompatActivity {
    int myUserId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        init();
    }
    private void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userName = "Winnie"; //TODO: get userName of current user
        db.collection("invitation")
                .whereEqualTo("username", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d("test", document.getId() + " => " + document.getData());
                                String acceptances = document.get("accept").toString();
                                Log.d(document.getId(), acceptances);
                                addMatchButtons(document.getId(), acceptances);
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
        // TODO: add changes listener
    }

    private void addMatchButtons(String key, String value) {
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linearlayout02);
        String[] strings = value.split(", ");
        if (strings.length > 0) {
            TextView invitation = new TextView(this);
            invitation.setText("Invitation: " + key);
            linearLayout.addView(invitation);
            for (String string : strings) {
                TextView user = new TextView(this);
                user.setText("user " + string);
                Button button = new Button(this);
                button.setText("match!");
                // TODO: add button listener
                linearLayout.addView(user);
                linearLayout.addView(button);
            }
        }
    }
}
