package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Accepted extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        init();
    }
    private void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("invitation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //View view= LayoutInflater.from(getContext()).inflate(R.layout.post_layout,null);
                                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.post_layout, null);
                                TextView username_field = (TextView) layout.findViewById(R.id.username);
                                if(username_field == null) Log.d("test","error.....");
                                Object username_ = document.getData().get("username");
                                if(username_ == null) continue;
                                String username = username_.toString();
                                //Log.d("test", username);
                                //username_field.setText(username);
                                LinearLayout container = (LinearLayout) findViewById(R.id.linearlayout02);
                                container.addView(layout);
                                //Log.d("test", document.getId() + " => " + document.getData().get("username"));
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}