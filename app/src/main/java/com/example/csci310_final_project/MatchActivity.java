package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

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
        // Map<String, String> all_acceptances = new HashMap<String, String>();
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linearlayout02);

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
                                // all_acceptances.put(document.getId(), acceptances);
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*
        for (Map.Entry<String, String> entry : all_acceptances.entrySet()) {
            Log.d("test", "here");
            String key = entry.getKey();
            String value = entry.getValue();

            TextView user = new TextView(this);
            user.setText("users: " + value);
            Button button = new Button(this);
            button.setText("match!");
            linearLayout.addView(user);
            linearLayout.addView(button);
        }
         */
    }
}
